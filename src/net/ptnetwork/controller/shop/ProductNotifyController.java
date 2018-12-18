/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ptnetwork.Results;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.ProductNotify;
import net.ptnetwork.entity.Sku;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.ProductNotifyService;
import net.ptnetwork.service.SkuService;

/**
 * Controller - 到货通知
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("shopProductNotifyController")
@RequestMapping("/product_notify")
public class ProductNotifyController extends BaseController {

	@Inject
	private ProductNotifyService productNotifyService;
	@Inject
	private SkuService skuService;

	/**
	 * 获取当前会员E-mail
	 */
	@GetMapping("/email")
	public ResponseEntity<?> email(@CurrentUser Member currentUser) {
		String email = currentUser != null ? currentUser.getEmail() : null;
		Map<String, String> data = new HashMap<>();
		data.put("email", email);
		return ResponseEntity.ok(data);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(String email, Long skuId, @CurrentUser Member currentUser) {
		if (!isValid(ProductNotify.class, "email", email)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Sku sku = skuService.find(skuId);
		if (sku == null) {
			return Results.unprocessableEntity("shop.productNotify.skuNotExist");
		}
		if (!sku.getIsActive()) {
			return Results.unprocessableEntity("shop.productNotify.skuNotActive");
		}
		if (!sku.getIsMarketable()) {
			return Results.unprocessableEntity("shop.productNotify.skuNotMarketable");
		}
		if (!sku.getIsOutOfStock()) {
			return Results.unprocessableEntity("shop.productNotify.skuInStock");
		}
		if (productNotifyService.exists(sku, email)) {
			return Results.unprocessableEntity("shop.productNotify.exist");
		}
		ProductNotify productNotify = new ProductNotify();
		productNotify.setEmail(email);
		productNotify.setHasSent(false);
		productNotify.setMember(currentUser);
		productNotify.setSku(sku);
		productNotifyService.save(productNotify);
		return Results.OK;
	}

}