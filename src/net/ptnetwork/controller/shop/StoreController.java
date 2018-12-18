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

import net.ptnetwork.entity.Store;
import net.ptnetwork.exception.ResourceNotFoundException;
import net.ptnetwork.service.StoreProductCategoryService;
import net.ptnetwork.service.StoreProductTagService;
import net.ptnetwork.service.StoreService;

/**
 * Controller - 店铺
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("shopStoreController")
@RequestMapping("/store")
public class StoreController extends BaseController {

	@Inject
	private StoreService storeService;
	@Inject
	private StoreProductCategoryService storeProductCategoryService;
	@Inject
	private StoreProductTagService storeProductTagService;

	/**
	 * 首页
	 */
	@GetMapping("/{storeId}")
	public String index(@PathVariable Long storeId, ModelMap model) {
		Store store = storeService.find(storeId);
		if (store == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("store", store);
		model.addAttribute("storeProductCategoryTree", storeProductCategoryService.findTree(store));
		model.addAttribute("storeProductTags", storeProductTagService.findList(store, true));
		return "shop/store/index";
	}

}