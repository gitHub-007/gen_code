/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.business;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Business;
import net.ptnetwork.entity.Cash;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.CashService;

/**
 * Controller - 提现
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("businessCashController")
@RequestMapping("/business/cash")
public class CashController extends BaseController {

	@Inject
	private CashService cashService;

	/**
	 * 检查余额
	 */
	@GetMapping("/check_balance")
	public @ResponseBody boolean checkBalance(BigDecimal amount, @CurrentUser Business currentUser) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			return false;
		}
		return currentUser.getBalance().compareTo(amount) >= 0;
	}

	/**
	 * 申请提现
	 */
	@GetMapping("/application")
	public String cash() {
		return "business/cash/application";
	}

	/**
	 * 申请提现
	 */
	@PostMapping("/save")
	public String applyCash(Cash cash, @CurrentUser Business currentUser, RedirectAttributes redirectAttributes) {
		if (!isValid(cash)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (currentUser.getBalance().compareTo(cash.getAmount()) < 0) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		cashService.applyCash(cash, currentUser);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, @CurrentUser Business currentUser, ModelMap model) {
		model.addAttribute("page", cashService.findPage(currentUser, pageable));
		return "business/cash/list";
	}

}