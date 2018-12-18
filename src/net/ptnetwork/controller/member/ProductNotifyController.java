/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.member;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.ptnetwork.Pageable;
import net.ptnetwork.Results;
import net.ptnetwork.entity.BaseEntity;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.ProductNotify;
import net.ptnetwork.exception.UnauthorizedException;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.ProductNotifyService;

/**
 * Controller - 到货通知
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("memberProductNotifyController")
@RequestMapping("/member/product_notify")
public class ProductNotifyController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Inject
	private ProductNotifyService productNotifyService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long productNotifyId, @CurrentUser Member currentUser, ModelMap model) {
		ProductNotify productNotify = productNotifyService.find(productNotifyId);
		if (productNotify != null && !currentUser.equals(productNotify.getMember())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("productNotify", productNotify);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Integer pageNumber, @CurrentUser Member currentUser, Model model) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", productNotifyService.findPage(null, currentUser, null, null, null, pageable));
		return "member/product_notify/list";
	}

	/**
	 * 列表
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> list(Integer pageNumber, @CurrentUser Member currentUser) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(productNotifyService.findPage(null, currentUser, null, null, null, pageable).getContent());
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@ModelAttribute(binding = false) ProductNotify productNotify) {
		if (productNotify == null) {
			return Results.NOT_FOUND;
		}

		productNotifyService.delete(productNotify);
		return Results.OK;
	}

}