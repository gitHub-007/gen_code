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
import net.ptnetwork.entity.MemberRank;
import net.ptnetwork.entity.ProductCategory;
import net.ptnetwork.entity.Promotion;
import net.ptnetwork.entity.Store;

/**
 * Dao - 促销
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface PromotionDao extends BaseDao<Promotion, Long> {

	/**
	 * 查找促销
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param isEnabled
	 *            是否开启
	 * @return 促销
	 */
	List<Promotion> findList(Store store, Promotion.Type type, Boolean isEnabled);

	/**
	 * 查找促销
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param memberRank
	 *            会员等级
	 * @param productCategory
	 *            商品分类
	 * @param hasBegun
	 *            是否已开始
	 * @param hasEnded
	 *            是否已结束
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 促销
	 */
	List<Promotion> findList(Store store, Promotion.Type type, MemberRank memberRank, ProductCategory productCategory, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找促销
	 * 
	 * @param store
	 *            店铺
	 * @param type
	 *            类型
	 * @param pageable
	 *            分页
	 * @return 促销分页
	 */
	Page<Promotion> findPage(Store store, Promotion.Type type, Pageable pageable);

}