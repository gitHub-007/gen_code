/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import java.util.List;

import net.ptnetwork.entity.SitemapUrl;

/**
 * Service - Sitemap URL
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface SitemapUrlService {

	/**
	 * 生成Sitemap URL
	 * 
	 * @param type
	 *            类型
	 * @param changefreq
	 *            更新频率
	 * @param priority
	 *            权重
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return Sitemap URL
	 */
	List<SitemapUrl> generate(SitemapUrl.Type type, SitemapUrl.Changefreq changefreq, float priority, Integer first, Integer count);

	/**
	 * 查询Sitemap URL数量
	 * 
	 * @param type
	 *            类型
	 * @return Sitemap URL数量
	 */
	Long count(SitemapUrl.Type type);

}