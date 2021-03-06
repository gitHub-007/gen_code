/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import net.ptnetwork.TemplateConfig;

/**
 * Service - 模板
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface TemplateService {

	/**
	 * 读取模板文件内容
	 * 
	 * @param templateConfigId
	 *            模板配置ID
	 * @return 模板文件内容
	 */
	String read(String templateConfigId);

	/**
	 * 读取模板文件内容
	 * 
	 * @param templateConfig
	 *            模板配置
	 * @return 模板文件内容
	 */
	String read(TemplateConfig templateConfig);

	/**
	 * 写入模板文件内容
	 * 
	 * @param templateConfigId
	 *            模板配置ID
	 * @param content
	 *            模板文件内容
	 */
	void write(String templateConfigId, String content);

	/**
	 * 写入模板文件内容
	 * 
	 * @param templateConfig
	 *            模板配置
	 * @param content
	 *            模板文件内容
	 */
	void write(TemplateConfig templateConfig, String content);

}