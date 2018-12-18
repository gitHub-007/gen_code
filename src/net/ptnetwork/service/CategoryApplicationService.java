/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.CategoryApplication;
import net.ptnetwork.entity.ProductCategory;
import net.ptnetwork.entity.Store;

/**
 * Service - 经营分类申请
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface CategoryApplicationService extends BaseService<CategoryApplication, Long> {

	/**
	 * 判断经营分类申请是否存在
	 * 
	 * @param store
	 *            店铺
	 * @param productCategory
	 *            经营分类
	 * @param status
	 *            状态
	 * @return 经营分类申请是否存在
	 */
	boolean exist(Store store, ProductCategory productCategory, CategoryApplication.Status status);

	/**
	 * 查找经营分类申请分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 经营分类申请分页
	 */
	Page<CategoryApplication> findPage(Store store, Pageable pageable);

	/**
	 * 审核经营分类申请
	 * 
	 * @param categoryApplication
	 *            经营分类申请
	 * @param isPassed
	 *            是否审核通过
	 */
	void review(CategoryApplication categoryApplication, boolean isPassed);

}