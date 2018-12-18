/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.ptnetwork.Message;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Admin;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.PointLog;
import net.ptnetwork.security.CurrentUser;
import net.ptnetwork.service.MemberService;
import net.ptnetwork.service.PointLogService;

/**
 * Controller - 积分
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Controller("adminPointController")
@RequestMapping("/admin/point")
public class PointController extends BaseController {

	@Inject
	private PointLogService pointLogService;
	@Inject
	private MemberService memberService;

	/**
	 * 检查会员
	 */
	@GetMapping("/check_member")
	public @ResponseBody Map<String, Object> checkMember(String username) {
		Map<String, Object> data = new HashMap<>();
		Member member = memberService.findByUsername(username);
		if (member == null) {
			data.put("message", Message.warn("admin.point.memberNotExist"));
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);
		data.put("point", member.getPoint());
		return data;
	}

	/**
	 * 调整
	 */
	@GetMapping("/adjust")
	public String adjust() {
		return "admin/point/adjust";
	}

	/**
	 * 调整
	 */
	@PostMapping("/adjust")
	public String adjust(String username, long amount, String memo, @CurrentUser Admin currentUser, RedirectAttributes redirectAttributes) {
		Member member = memberService.findByUsername(username);
		if (member == null) {
			return ERROR_VIEW;
		}
		if (amount == 0) {
			return ERROR_VIEW;
		}
		if (member.getPoint() == null || member.getPoint() + amount < 0) {
			return ERROR_VIEW;
		}
		memberService.addPoint(member, amount, PointLog.Type.adjustment, memo);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:log";
	}

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public String log(Long memberId, Pageable pageable, ModelMap model) {
		Member member = memberService.find(memberId);
		if (member != null) {
			model.addAttribute("member", member);
			model.addAttribute("page", pointLogService.findPage(member, pageable));
		} else {
			model.addAttribute("page", pointLogService.findPage(pageable));
		}
		return "admin/point/log";
	}

}