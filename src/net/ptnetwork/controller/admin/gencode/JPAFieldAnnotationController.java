/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.controller.admin.gencode;

import net.ptnetwork.controller.admin.BaseController;
import javax.inject.Inject;

import net.ptnetwork.entity.model.JPAFieldAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.Message;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.BaseEntity;
import net.ptnetwork.service.gencode.JPAFieldAnnotationService;

/**
 * Created by CodeGenerator
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:26
 * @Description:JPAFieldAnnotation()
 */
@Controller("gencodeJPAFieldAnnotationController")
@RequestMapping("admin/gencode/jPAFieldAnnotation")
public class JPAFieldAnnotationController extends BaseController {

	@Inject
	private JPAFieldAnnotationService jPAFieldAnnotationService;


	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "gencode/jPAFieldAnnotation/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(JPAFieldAnnotation jPAFieldAnnotation, RedirectAttributes redirectAttributes) {
		if (!isValid(jPAFieldAnnotation, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		jPAFieldAnnotationService.save(jPAFieldAnnotation);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("jPAFieldAnnotation", jPAFieldAnnotationService.find(id));
		return "gencode/jPAFieldAnnotation/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(JPAFieldAnnotation jPAFieldAnnotation,RedirectAttributes redirectAttributes) {
		if (!isValid(jPAFieldAnnotation)) {
			return ERROR_VIEW;
		}
		jPAFieldAnnotationService.update(jPAFieldAnnotation, "id");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", jPAFieldAnnotationService.findPage(pageable));
		return "gencode/jPAFieldAnnotation/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		jPAFieldAnnotationService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}