/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.controller.admin.gencode;

import net.ptnetwork.Filter;
import net.ptnetwork.Message;
import net.ptnetwork.Pageable;
import net.ptnetwork.controller.admin.BaseController;
import net.ptnetwork.entity.Admin;
import net.ptnetwork.entity.BaseEntity;
import net.ptnetwork.entity.model.JPAProject;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.gencode.JPAProjectService;
import net.ptnetwork.util.CompressUtils;
import noah.AntOP;
import noah.Copyright;
import noah.ManageWorkingCopy;
import noah.SvnServeOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tmatesoft.svn.core.SVNURL;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by CodeGenerator
 *
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 17:32:28
 * @Description:JPAProject()
 */
@Controller("gencodeJPAProjectController")
@RequestMapping("admin/gencode/jPAProject")
public class JPAProjectController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(JPAProjectController.class);

    @Inject
    private JPAProjectService jPAProjectService;

    @Inject
    private TaskExecutor taskExecutor;

    @Value("${project.src.path}")
    private String projectSrcPath;

    @Value("${user_code.base}")
    private String userCodeBase;

    @Value("${svn.rootRepo}")
    private String rootRepo;

    @Value("${base_code.loader_path}")
    private String baseCode;

    @Inject
    private CodeConst codeConst;

    /**
     * 添加
     */
    @GetMapping("/add")
    public String add(ModelMap model) {
        return "gencode/jPAProject/add";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public String save(@CurrentUser Admin admin, JPAProject jPAProject, HttpServletRequest request,
                       RedirectAttributes redirectAttributes) throws Exception {
        if (!isValid(jPAProject, BaseEntity.Save.class)) {
            return ERROR_VIEW;
        }
        jPAProjectService.save(jPAProject);
        Path userCodePath = Paths.get(userCodeBase + jPAProject.getName()).toAbsolutePath();
        String projectPath = String.format("%s%s", rootRepo, jPAProject.getName());
        SvnServeOperator.createRepo(projectPath);
        SvnServeOperator.modifyConf(Paths.get(projectPath), admin.getUsername(), codeConst.getDefaultPwd());
        ManageWorkingCopy manageWorkingCopy = new ManageWorkingCopy(admin.getUsername(), codeConst.getDefaultPwd());
        //创建svn副本工作区
        manageWorkingCopy.doCheckout(SVNURL.parseURIEncoded(String.format("%s/%s", codeConst.getIpPort(),
                                                                          jPAProject.getName())), userCodePath);
        //创建项目
        Path baseCodePath = Paths.get(request.getServletContext().getRealPath(baseCode)).toAbsolutePath();
        CompressUtils.extract(baseCodePath.toFile(), userCodePath.toFile(), StandardCharsets.UTF_8.name());
        Copyright copyright = new Copyright(userCodePath.toString(), "ptnetwork", jPAProject.getCopyright().trim());
        copyright.modifyCopyRight();
        //修改.project的name
        Path dotProjectPath = userCodePath.resolve(".project");
        Copyright.modifyProjectName(dotProjectPath, jPAProject.getName());
        //将相关文件纳入版本控制
        taskExecutor.execute(() -> manageWorkingCopy.doCommit(userCodePath, "初始化项目"));
        logger.info("###########生成项目文件的路径为###########:{}", userCodePath);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit(Long id, ModelMap model) {
        model.addAttribute("jPAProject", jPAProjectService.find(id));
        return "gencode/jPAProject/edit";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public String update(JPAProject jPAProject, RedirectAttributes redirectAttributes) {
        if (!isValid(jPAProject)) {
            return ERROR_VIEW;
        }
        jPAProjectService.update(jPAProject, "id");
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("page", jPAProjectService.findPage(pageable));
        model.addAttribute("svnAddr", codeConst.getIpPort());
        return "gencode/jPAProject/list";
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public @ResponseBody
    Message delete(Long[] ids) {
        jPAProjectService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    /**
     * 编译项目
     */
    @PostMapping("/compile")
    public @ResponseBody
    Message compile(String projectName) {
        Path buildPath = Paths.get(projectSrcPath).toAbsolutePath();
        Path userCodePath = Paths.get(userCodeBase).resolve(projectName).toAbsolutePath();
        if (!userCodePath.toFile().exists()) {
            return Message.warn("代码位置不存在,请联系管理员");
        }
        AntOP antOP =
                new AntOP().buildFile(buildPath).setBaseDir(Paths.get(userCodePath.toString())).addOutputPrintStream().addErrorPrintStream().addEventListener();
        System.out.println(antOP.getProject().getBaseDir());
        taskExecutor.execute(() -> antOP.exeDefaultTarget());
        return SUCCESS_MESSAGE;
    }

    @GetMapping("/uniqueName")
    @ResponseBody
//    @JsonView(BaseEntity.BaseView.class)
//    public ResponseEntity<JPAProject> uniqueName(String name) {
    //        return Results.status(HttpStatus.OK, jPAProjectService.find(1411L));
    public boolean uniqueName(String name) {
        return jPAProjectService.count(Filter.eq("name", name, true)) > 0 ? false : true;
    }
}