/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.business;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.Pageable;
import net.ptnetwork.Results;
import net.ptnetwork.entity.Review;
import net.ptnetwork.entity.Store;
import net.ptnetwork.exception.UnauthorizedException;
import net.ptnetwork.security.CurrentStore;
import net.ptnetwork.service.ReviewService;

/**
 * Controller - 评论
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("businessReviewController")
@RequestMapping("/business/review")
public class ReviewController extends BaseController {

	@Inject
	private ReviewService reviewService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long reviewId, @CurrentStore Store currentStore, ModelMap model) {
		Review review = reviewService.find(reviewId);
		if (review != null && !currentStore.equals(review.getStore())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("review", review);
	}

	/**
	 * 回复
	 */
	@GetMapping("/reply")
	public String reply(@ModelAttribute(binding = false) Review review, ModelMap model) {
		if (review == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("review", review);
		return "business/review/reply";
	}

	/**
	 * 回复
	 */
	@PostMapping("/reply")
	public String reply(@ModelAttribute(binding = false) Review review, String content, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!isValid(Review.class, "content", content)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (review == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		Review replyReview = new Review();
		replyReview.setContent(content);
		replyReview.setIp(request.getRemoteAddr());
		review.setIsShow(true);
		reviewService.reply(review, replyReview);
		reviewService.update(review);

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:reply?reviewId=" + review.getId();
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(@ModelAttribute(binding = false) Review review, ModelMap model) {
		if (review == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("review", review);
		return "business/review/edit";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Review.Type type, Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("types", Review.Type.values());
		model.addAttribute("page", reviewService.findPage(null, null, currentStore, type, null, pageable));
		return "business/review/list";
	}

	/**
	 * 删除回复
	 */
	@PostMapping("/delete_reply")
	public ResponseEntity<?> deleteReply(@ModelAttribute(binding = false) Review review) {
		if (review == null || review.getForReview() == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		reviewService.delete(review);
		return Results.OK;
	}

}