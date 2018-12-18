/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.member;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ptnetwork.Pageable;
import net.ptnetwork.Results;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.SocialUser;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.SocialUserService;

/**
 * Controller - 社会化用户
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("memberSocialUserController")
@RequestMapping("/member/social_user")
public class SocialUserController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Inject
	private SocialUserService socialUserService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", socialUserService.findPage(currentUser, pageable));
		return "member/social_user/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long id, @CurrentUser Member currentUser) {
		if (id == null) {
			return Results.NOT_FOUND;
		}
		SocialUser socialUser = socialUserService.find(id);
		if (socialUser == null || !currentUser.equals(socialUser.getUser())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		socialUserService.delete(socialUser);
		return Results.OK;
	}

}