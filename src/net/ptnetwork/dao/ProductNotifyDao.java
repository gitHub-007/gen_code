/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.ProductNotify;
import net.ptnetwork.entity.Sku;
import net.ptnetwork.entity.Store;

/**
 * Dao - 到货通知
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface ProductNotifyDao extends BaseDao<ProductNotify, Long> {

	/**
	 * 判断到货通知是否存在
	 * 
	 * @param sku
	 *            SKU
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 到货通知是否存在
	 */
	boolean exists(Sku sku, String email);

	/**
	 * 查找到货通知分页
	 * 
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            SKU是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @param pageable
	 *            分页信息
	 * @return 到货通知分页
	 */
	Page<ProductNotify> findPage(Store store, Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable);

	/**
	 * 查找到货通知数量
	 * 
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            SKU是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @return 到货通知数量
	 */
	Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent);

}