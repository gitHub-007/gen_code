/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.controller.admin.gencode;

import cn.org.rapid_framework.generator.GeneratorFacade;
import net.ptnetwork.Filter;
import net.ptnetwork.Message;
import net.ptnetwork.Pageable;
import net.ptnetwork.controller.admin.BaseController;
import net.ptnetwork.entity.Admin;
import net.ptnetwork.entity.BaseEntity;
import net.ptnetwork.entity.model.FieldBean;
import net.ptnetwork.entity.model.JPAEntity;
import net.ptnetwork.entity.model.JPAProject;
import net.ptnetwork.entity.model.JavaBasicTypeDic;
import net.ptnetwork.filter.MyShiroFilterFactoryBean;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.gencode.EntityAssociationService;
import net.ptnetwork.service.gencode.JPAEntityService;
import net.ptnetwork.service.gencode.JPAProjectService;
import net.ptnetwork.util.JsonUtils;
import noah.ManageWorkingCopy;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by CodeGenerator
 *
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:25
 * @Description:JPAEntity()
 */
@Controller("gencodeJPAEntityController")
@RequestMapping("admin/gencode/jPAEntity")
public class JPAEntityController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(JPAEntityController.class);

    @Inject
    private JPAEntityService jPAEntityService;
    @Inject
    private JPAProjectService jpaProjectService;
    @Inject
    private EntityAssociationService entityAssociationService;
    @Value("${user_code.base}")
    private String userCodeBase;
    @Value("${gg_isOverride}")
    private boolean gg_isOverride;

    @Inject
    private CodeConst codeConst;

    @Inject
    private TaskExecutor taskExecutor;

    @Inject
    private MyShiroFilterFactoryBean myShiroFilterFactoryBean;

    @GetMapping("/reload")
    @ResponseBody
    public void reloadFilterChains() {
        synchronized (myShiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) myShiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver =
                    (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空初始权限配置
            manager.getFilterChains().clear();
            myShiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            // 重新构建生成
            myShiroFilterFactoryBean.setFilterChainDefinitionMap(new LinkedHashMap<>());
            Map<String, String> chains = myShiroFilterFactoryBean.getFilterChainDefinitionMap();
            chains.put("/admin", "anon");
            chains.put("/admin/", "anon");
            chains.put("/admin/**", "perms[\"999\"]");

            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            logger.debug("update shiro permission success...");
        }
    }

    /**
     * 添加
     */
    @GetMapping("/add")
    public String add(Long projectId, ModelMap model) {
        addSelectAttr(model);
        model.addAttribute("projectId", projectId);
        return "gencode/jPAEntity/add";
    }

    private String replaceStr(String str) {
        return StringUtils.substringBetween(str, "[", "]").replace("\"", "");
    }

    private ModelMap addSelectAttr(ModelMap model) {
        model.addAttribute("basicTypeSelect", replaceStr(JsonUtils.toJson(JavaBasicTypeDic.BasicType.values())));
        model.addAttribute("associationSelect", replaceStr(JsonUtils.toJson(JavaBasicTypeDic.AssosationType.values())));
        model.addAttribute("collectionTypeSelect",
                           replaceStr(JsonUtils.toJson(JavaBasicTypeDic.CollectionType.values())));
        return model;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public String save(@CurrentUser Admin admin, JPAEntity jPAEntity, Long projectId, FieldBean fieldBean,
                       RedirectAttributes redirectAttributes) {
        if (!isValid(jPAEntity, BaseEntity.Save.class)) {
            return ERROR_VIEW;
        }
        jPAEntity.setCreateUser(String.format("%s(%s)", admin.getUsername(), admin.getName()));
        jPAEntityService.saveEntity(jPAEntity, projectId, fieldBean);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit(Long id, Long projectId, ModelMap model) {
        addSelectAttr(model);
        model.addAttribute("projectId", projectId);
        model.addAttribute("javaTypes", JavaBasicTypeDic.BasicType.values());
        model.addAttribute("associations", JavaBasicTypeDic.AssosationType.values());
        model.addAttribute("collections", JavaBasicTypeDic.CollectionType.values());
        model.addAttribute("entityAssociations", entityAssociationService.getJpaEntity(id));
        model.addAttribute("jPAEntity", jPAEntityService.find(id));
        return "gencode/jPAEntity/edit";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public String update(JPAEntity jPAEntity, FieldBean fieldBean, RedirectAttributes redirectAttributes) {
        if (!isValid(jPAEntity)) {
            return ERROR_VIEW;
        }
        jPAEntityService.updateEntity(jPAEntity, fieldBean);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Long projectId, Pageable pageable, ModelMap model) {
        pageable.setFilters(Arrays.asList(Filter.eq("jpaProject.id", projectId)));
        model.addAttribute("page", jPAEntityService.findPage(pageable));
        model.addAttribute("projectId", projectId);
        return "gencode/jPAEntity/list";
    }

    /**
     * 列表
     */
    @GetMapping("/openList")
    public String openList(Long projectId, Pageable pageable, ModelMap model) {
        pageable.setFilters(Arrays.asList(Filter.eq("jpaProject.id", projectId)));
        model.addAttribute("page", jPAEntityService.findPage(pageable));
        model.addAttribute("projectId", projectId);
        return "gencode/jPAEntity/openList";
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public @ResponseBody
    Message delete(Long[] ids) {
        jPAEntityService.delete(ids);
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     */
    @GetMapping("/configMap")
    public String configMap(String ids, Long projectId, ModelMap model) {
        model.addAttribute("ids", ids);
        model.addAttribute("projectId", projectId);
        return "gencode/jPAEntity/genCode";
    }

    /**
     * 生成代码
     */
    @PostMapping("/genCode")
    @ResponseBody
    public Message genCode(String ids, Long projectId, @RequestParam Map<String, Object> configMap,
                           @CurrentUser Admin admin) {
        JPAProject project = jpaProjectService.find(projectId);
        configMap.put("gg_isOverride", gg_isOverride);
        configMap.put("author", String.format("%s[%s]", admin.getName(), admin.getUsername()));
        Path outRoot = Paths.get(userCodeBase).resolve(project.getName()).toAbsolutePath();
        configMap.put("outRoot", outRoot.toString());
        configMap.put("copyright", project.getCopyright());
        if (!(outRoot.toFile()).exists()) {
            return Message.warn("生成代码失败,项目路径不存在.请联系管理员");
        }
        try {
            List<Long> idList =
                    Arrays.stream(ids.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toList());
            List<JPAEntity> jpaEntities = jPAEntityService.findList(idList.toArray(new Long[idList.size()]));
            jpaEntities.stream().forEach(entity -> entity.setEntityAssociations(entityAssociationService.getJpaEntity(entity.getId())));
            GeneratorFacade generatorFacade = new GeneratorFacade(configMap);
            generatorFacade.getGenerator().addClassPathTemplateRootDir("template/template-entity");
            generatorFacade.generateByEntity(jpaEntities.toArray(new JPAEntity[jpaEntities.size()]));
            Path path = Paths.get((String) configMap.get("outRoot")).toAbsolutePath();
            logger.info("生成文件的路径为:{}", path.toString());
            taskExecutor.execute(() -> {
                ManageWorkingCopy manageWorkingCopy = new ManageWorkingCopy(admin.getUsername(),
                                                                            codeConst.getDefaultPwd());
                //更新svn副本工作区
                manageWorkingCopy.doUpdate(path);
                //将相关文件纳入版本控制
                manageWorkingCopy.doCommit(path, "提交生成的新文件");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS_MESSAGE;
    }

    @GetMapping("/uniqueName")
    @ResponseBody
    public boolean uniqueName(long projectId, String name) {
        return jPAEntityService.count(Filter.eq("jpaProject", projectId), Filter.eq("name", name, false)) > 0 ?
                false : true;
    }

}