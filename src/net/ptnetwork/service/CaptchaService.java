/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import java.awt.image.BufferedImage;

/**
 * Service - 验证码
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface CaptchaService {

	/**
	 * 创建验证码图片
	 * 
	 * @param captchaId
	 *            验证码ID
	 * @return 验证码图片
	 */
	BufferedImage createImage(String captchaId);

	/**
	 * 验证码验证
	 * 
	 * @param captchaId
	 *            验证码ID
	 * @param captcha
	 *            验证码(忽略大小写)
	 * @return 验证码验证是否通过
	 */
	boolean isValid(String captchaId, String captcha);

}