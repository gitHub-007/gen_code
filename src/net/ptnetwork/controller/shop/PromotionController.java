/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.shop;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ptnetwork.entity.Promotion;
import net.ptnetwork.exception.ResourceNotFoundException;
import net.ptnetwork.service.PromotionService;

/**
 * Controller - 促销
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("shopPromotionController")
@RequestMapping("/promotion")
public class PromotionController extends BaseController {

	@Inject
	private PromotionService promotionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{promotionId}")
	public String detail(@PathVariable Long promotionId, ModelMap model) {
		Promotion promotion = promotionService.find(promotionId);
		if (promotion == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("promotion", promotion);
		return "shop/promotion/detail";
	}

}