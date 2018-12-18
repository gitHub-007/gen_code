/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.InstantMessage;
import net.ptnetwork.entity.Store;

/**
 * Dao - 即时通讯
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface InstantMessageDao extends BaseDao<InstantMessage, Long> {

	/**
	 * 查找即时通讯分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 即时通讯分页
	 */
	Page<InstantMessage> findPage(Store store, Pageable pageable);

}