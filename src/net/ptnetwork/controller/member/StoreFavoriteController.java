/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.member;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.ptnetwork.Pageable;
import net.ptnetwork.Results;
import net.ptnetwork.entity.BaseEntity;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.Store;
import net.ptnetwork.entity.StoreFavorite;
import net.ptnetwork.exception.UnauthorizedException;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.StoreFavoriteService;
import net.ptnetwork.service.StoreService;

/**
 * Controller - 店铺收藏
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("memberStoreFavoriteController")
@RequestMapping("/member/store_favorite")
public class StoreFavoriteController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Inject
	private StoreFavoriteService storeFavoriteService;
	@Inject
	private StoreService storeService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long storeId, Long storeFavoriteId, @CurrentUser Member currentUser, ModelMap model) {
		model.addAttribute("store", storeService.find(storeId));

		StoreFavorite storeFavorite = storeFavoriteService.find(storeFavoriteId);
		if (storeFavorite != null && !currentUser.equals(storeFavorite.getMember())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("storeFavorite", storeFavorite);
	}

	/**
	 * 添加
	 */
	@PostMapping("/add")
	public ResponseEntity<?> add(@ModelAttribute(binding = false) Store store, @CurrentUser Member currentUser) {
		if (store == null) {
			return Results.NOT_FOUND;
		}

		if (storeFavoriteService.exists(currentUser, store)) {
			return Results.unprocessableEntity("member.storeFavorite.exist");
		}
		if (StoreFavorite.MAX_STORE_FAVORITE_SIZE != null && storeFavoriteService.count(currentUser) >= StoreFavorite.MAX_STORE_FAVORITE_SIZE) {
			return Results.unprocessableEntity("member.storeFavorite.addCountNotAllowed", StoreFavorite.MAX_STORE_FAVORITE_SIZE);
		}
		StoreFavorite storeFavorite = new StoreFavorite();
		storeFavorite.setMember(currentUser);
		storeFavorite.setStore(store);
		storeFavoriteService.save(storeFavorite);
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", storeFavoriteService.findPage(currentUser, pageable));
		return "member/store_favorite/list";
	}

	/**
	 * 列表
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> list(Integer pageNumber, @CurrentUser Member currentUser) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(storeFavoriteService.findPage(currentUser, pageable).getContent());
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@ModelAttribute(binding = false) StoreFavorite storeFavorite) {
		if (storeFavorite == null) {
			return Results.NOT_FOUND;
		}

		storeFavoriteService.delete(storeFavorite);
		return Results.OK;
	}

}