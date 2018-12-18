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

import net.ptnetwork.Message;
import net.ptnetwork.Pageable;
import net.ptnetwork.service.OrderPaymentService;

/**
 * Controller - 订单支付
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("adminOrderPaymentController")
@RequestMapping("/admin/order_payment")
public class OrderPaymentController extends BaseController {

	@Inject
	private OrderPaymentService orderPaymentService;

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(Long id, ModelMap model) {
		model.addAttribute("orderPayment", orderPaymentService.find(id));
		return "admin/order_payment/view";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", orderPaymentService.findPage(pageable));
		return "admin/order_payment/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		orderPaymentService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}