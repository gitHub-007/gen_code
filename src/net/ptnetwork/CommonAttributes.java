/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork;

/**
 * 公共参数
 *
 * @author PTNETWORK Team
 * @version 5.0
 */
public final class CommonAttributes {

	/**
	 * 日期格式配比
	 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/**
	 * ptnetwork.xml文件路径
	 */
	public static final String PTNETWORK_XML_PATH = "/ptnetwork.xml";

	/**
	 * ptnetwork.properties文件路径
	 */
	public static final String PTNETWORK_PROPERTIES_PATH = "/ptnetwork.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}