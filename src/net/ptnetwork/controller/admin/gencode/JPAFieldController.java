/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.controller.admin.gencode;

import net.ptnetwork.controller.admin.BaseController;
import javax.inject.Inject;

import net.ptnetwork.entity.model.JPAField;
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
import net.ptnetwork.service.gencode.JPAFieldService;

/**
 * Created by CodeGenerator
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:26
 * @Description:JPAField()
 */
@Controller("gencodeJPAFieldController")
@RequestMapping("admin/gencode/jPAField")
public class JPAFieldController extends BaseController {



	@Inject
	private JPAFieldService jPAFieldService;


	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "gencode/jPAField/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(JPAField jPAField, RedirectAttributes redirectAttributes) {
		if (!isValid(jPAField, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		jPAFieldService.save(jPAField);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("jPAField", jPAFieldService.find(id));
		return "gencode/jPAField/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(JPAField jPAField,RedirectAttributes redirectAttributes) {
		if (!isValid(jPAField)) {
			return ERROR_VIEW;
		}
		jPAFieldService.update(jPAField, "id");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", jPAFieldService.findPage(pageable));
		return "gencode/jPAField/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		jPAFieldService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}