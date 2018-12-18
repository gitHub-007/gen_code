/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Store;
import net.ptnetwork.entity.StoreAdImage;

/**
 * Service - 店铺广告图片
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface StoreAdImageService extends BaseService<StoreAdImage, Long> {

	/**
	 * 查找店铺广告图片分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 店铺广告图片分页
	 */
	Page<StoreAdImage> findPage(Store store, Pageable pageable);

}