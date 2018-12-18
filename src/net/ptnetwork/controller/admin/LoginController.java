/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ptnetwork.entity.Admin;
import net.ptnetwork.security.CurrentUser;

import java.time.LocalDate;

/**
 * Controller - 管理员登录
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("adminLoginController")
@RequestMapping("/admin")
public class LoginController extends BaseController {

	/**
	 * 登录跳转
	 */
	@GetMapping({ "", "/" })
	public String index() {
		return "redirect:/admin/login";
	}

	/**
	 * 登录页面
	 */
	@GetMapping("/login")
	public String index(@CurrentUser Admin currentUser, ModelMap model) {
		model.addAttribute("year", LocalDate.now().getYear());
		return currentUser != null ? "redirect:/admin/index" : "admin/login/index";
	}

}