/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Business;
import net.ptnetwork.entity.Cash;

/**
 * Service - 提现
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface CashService extends BaseService<Cash, Long> {

	/**
	 * 申请提现
	 * 
	 * @param cash
	 *            提现
	 * @param business
	 *            商家
	 */
	void applyCash(Cash cash, Business business);

	/**
	 * 查找提现记录分页
	 * 
	 * @param business
	 *            商家
	 * @param pageable
	 *            分页信息
	 * @return 提现记录分页
	 */
	Page<Cash> findPage(Business business, Pageable pageable);

	/**
	 * 审核提现
	 * 
	 * @param cash
	 *            提现
	 * @param isPassed
	 *            是否审核通过
	 */
	void review(Cash cash, Boolean isPassed);
}