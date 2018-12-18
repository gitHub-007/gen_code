/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Business;
import net.ptnetwork.entity.Sku;
import net.ptnetwork.entity.StockLog;
import net.ptnetwork.entity.Store;
import net.ptnetwork.exception.UnauthorizedException;
import net.ptnetwork.security.CurrentStore;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.SkuService;
import net.ptnetwork.service.StockLogService;

/**
 * Controller - 库存
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("businessStockController")
@RequestMapping("/business/stock")
public class StockController extends BaseController {

	@Inject
	private StockLogService stockLogService;
	@Inject
	private SkuService skuService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(String skuSn, @CurrentStore Store currentStore, ModelMap model) {
		Sku sku = skuService.findBySn(skuSn);
		if (sku != null && !currentStore.equals(sku.getStore())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("sku", sku);
	}

	/**
	 * SKU选择
	 */
	@GetMapping("/sku_select")
	public @ResponseBody List<Map<String, Object>> skuSelect(String keyword, @CurrentUser Business currentUser) {
		List<Map<String, Object>> data = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			return data;
		}
		List<Sku> skus = skuService.search(currentUser.getStore(), null, keyword, null, null);
		for (Sku sku : skus) {
			Map<String, Object> item = new HashMap<>();
			item.put("sn", sku.getSn());
			item.put("name", sku.getName());
			item.put("stock", sku.getStock());
			item.put("allocatedStock", sku.getAllocatedStock());
			item.put("specifications", sku.getSpecifications());
			data.add(item);
		}
		return data;
	}

	/**
	 * 入库
	 */
	@GetMapping("/stock_in")
	public String stockIn(Sku sku, ModelMap model) {
		model.addAttribute("sku", sku);
		return "business/stock/stock_in";
	}

	/**
	 * 入库
	 */
	@PostMapping("/stock_in")
	public String stockIn(Sku sku, Integer quantity, String memo, RedirectAttributes redirectAttributes) {
		if (sku == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (quantity == null || quantity <= 0) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		skuService.addStock(sku, quantity, StockLog.Type.stockIn, memo);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:log";
	}

	/**
	 * 出库
	 */
	@GetMapping("/stock_out")
	public String stockOut(Sku sku, ModelMap model) {
		model.addAttribute("sku", sku);
		return "business/stock/stock_out";
	}

	/**
	 * 出库
	 */
	@PostMapping("/stock_out")
	public String stockOut(Sku sku, Integer quantity, String memo, RedirectAttributes redirectAttributes) {
		if (sku == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (quantity == null || quantity <= 0) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (sku.getStock() - quantity < 0) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		skuService.addStock(sku, -quantity, StockLog.Type.stockOut, memo);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:log";
	}

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public String log(Pageable pageable, @CurrentUser Business currentUser, ModelMap model) {
		model.addAttribute("page", stockLogService.findPage(currentUser.getStore(), pageable));
		return "business/stock/log";
	}

}