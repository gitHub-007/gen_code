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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.service.CacheService;

/**
 * Controller - 缓存
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("adminCacheController")
@RequestMapping("/admin/cache")
public class CacheController extends BaseController {

	@Inject
	private CacheService cacheService;

	/**
	 * 缓存查看
	 */
	@GetMapping("/clear")
	public String clear(ModelMap model) {
		Long totalMemory = null;
		Long maxMemory = null;
		Long freeMemory = null;
		try {
			totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
			maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
			freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
		} catch (Exception e) {
		}
		model.addAttribute("totalMemory", totalMemory);
		model.addAttribute("maxMemory", maxMemory);
		model.addAttribute("freeMemory", freeMemory);
		model.addAttribute("cacheSize", cacheService.getCacheSize());
		model.addAttribute("diskStorePath", cacheService.getDiskStorePath());
		return "admin/cache/clear";
	}

	/**
	 * 清除缓存
	 */
	@PostMapping("/clear")
	public String clear(RedirectAttributes redirectAttributes) {
		cacheService.clear();
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:clear";
	}

}