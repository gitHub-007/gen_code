/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao;

import java.util.List;

import net.ptnetwork.Filter;
import net.ptnetwork.Order;
import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.Product;
import net.ptnetwork.entity.ProductFavorite;

/**
 * Dao - 商品收藏
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface ProductFavoriteDao extends BaseDao<ProductFavorite, Long> {

	/**
	 * 判断商品收藏是否存在
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @return 商品收藏是否存在
	 */
	boolean exists(Member member, Product product);

	/**
	 * 查找商品收藏
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 商品收藏
	 */
	List<ProductFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找商品收藏分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 商品收藏分页
	 */
	Page<ProductFavorite> findPage(Member member, Pageable pageable);

	/**
	 * 查找商品收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 商品收藏数量
	 */
	Long count(Member member);

}