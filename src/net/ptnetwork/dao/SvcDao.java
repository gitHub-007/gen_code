/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao;

import java.util.List;

import net.ptnetwork.Order;
import net.ptnetwork.entity.Store;
import net.ptnetwork.entity.StoreRank;
import net.ptnetwork.entity.Svc;

/**
 * Dao - 服务
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface SvcDao extends BaseDao<Svc, Long> {

	/**
	 * 查找服务
	 * 
	 * @param store
	 *            店铺
	 * @param promotionPluginId
	 *            促销插件Id
	 * @param storeRank
	 *            店铺等级
	 * @param orders
	 *            排序
	 * @return 服务
	 */
	List<Svc> find(Store store, String promotionPluginId, StoreRank storeRank, List<Order> orders);

}