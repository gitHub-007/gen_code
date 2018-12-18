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
import net.ptnetwork.entity.Brand;
import net.ptnetwork.entity.Product;
import net.ptnetwork.entity.ProductCategory;
import net.ptnetwork.entity.ProductTag;
import net.ptnetwork.service.BrandService;
import net.ptnetwork.service.ProductCategoryService;
import net.ptnetwork.service.ProductService;
import net.ptnetwork.service.ProductTagService;

/**
 * Controller - 商品
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

	@Inject
	private ProductService productService;
	@Inject
	private ProductCategoryService productCategoryService;
	@Inject
	private BrandService brandService;
	@Inject
	private ProductTagService productTagService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Product.Type type, Long productCategoryId, Long brandId, Long productTagId, Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		Brand brand = brandService.find(brandId);
		ProductTag productTag = productTagService.find(productTagId);

		model.addAttribute("types", Product.Type.values());
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("productTags", productTagService.findAll());
		model.addAttribute("type", type);
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("brandId", brandId);
		model.addAttribute("productTagId", productTagId);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("isList", isList);
		model.addAttribute("isTop", isTop);
		model.addAttribute("isActive", isActive);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("isStockAlert", isStockAlert);
		model.addAttribute("page", productService.findPage(type, null, productCategory, null, brand, null, productTag, null, null, null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, null, null, pageable));
		return "admin/product/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		productService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 上架商品
	 */
	@PostMapping("/shelves")
	public @ResponseBody Message shelves(Long[] ids) {
		if (ids == null) {
			return ERROR_MESSAGE;
		}
		for (Long id : ids) {
			if (!productService.exists(id)) {
				return ERROR_MESSAGE;
			}
			Product product = productService.find(id);
			if (product.getStore().hasExpired() || !product.getStore().getIsEnabled()) {
				return Message.error("admin.product.isShelvesSku", ERROR_MESSAGE);
			}
			if (!product.getIsMarketable()) {
				product.setIsMarketable(true);
				productService.update(product);
			}
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 下架商品
	 */
	@PostMapping("/shelf")
	public @ResponseBody Message shelf(Long[] ids) {
		if (ids == null) {
			return ERROR_MESSAGE;
		}
		for (Long id : ids) {
			if (!productService.exists(id)) {
				return ERROR_MESSAGE;
			}
			Product product = productService.find(id);
			if (product.getIsMarketable()) {
				product.setIsMarketable(false);
				productService.update(product);
			}
		}
		return SUCCESS_MESSAGE;
	}
}