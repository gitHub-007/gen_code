/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.Message;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.ArticleTag;
import net.ptnetwork.entity.BaseEntity;
import net.ptnetwork.service.ArticleTagService;

/**
 * Controller - 文章标签
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("adminArticleTagController")
@RequestMapping("/admin/article_tag")
public class ArticleTagController extends BaseController {

	@Inject
	private ArticleTagService articleTagService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/article_tag/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(ArticleTag articleTag, RedirectAttributes redirectAttributes) {
		if (!isValid(articleTag, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		articleTag.setArticles(null);
		articleTagService.save(articleTag);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("articleTag", articleTagService.find(id));
		return "admin/article_tag/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(ArticleTag articleTag, RedirectAttributes redirectAttributes) {
		if (!isValid(articleTag)) {
			return ERROR_VIEW;
		}
		articleTagService.update(articleTag, "articles");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", articleTagService.findPage(pageable));
		return "admin/article_tag/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		articleTagService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}