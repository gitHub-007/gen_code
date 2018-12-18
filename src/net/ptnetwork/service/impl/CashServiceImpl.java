/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.CashDao;
import net.ptnetwork.entity.Business;
import net.ptnetwork.entity.BusinessDepositLog;
import net.ptnetwork.entity.Cash;
import net.ptnetwork.service.BusinessService;
import net.ptnetwork.service.CashService;

/**
 * Service - 提现
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class CashServiceImpl extends BaseServiceImpl<Cash, Long> implements CashService {

	@Inject
	private CashDao cashDao;
	@Inject
	private BusinessService businessService;

	@Transactional(readOnly = true)
	public Page<Cash> findPage(Business business, Pageable pageable) {
		return cashDao.findPage(business, pageable);
	}

	public void applyCash(Cash cash, Business business) {
		Assert.notNull(cash);
		Assert.notNull(business);
		Assert.isTrue(cash.getAmount().compareTo(BigDecimal.ZERO) > 0);

		cash.setStatus(Cash.Status.pending);
		cash.setBusiness(business);
		cashDao.persist(cash);

		businessService.addBalance(cash.getBusiness(), cash.getAmount().negate(), BusinessDepositLog.Type.cash, null);
		businessService.addFrozenFund(business, cash.getAmount());

	}

	public void review(Cash cash, Boolean isPassed) {
		Assert.notNull(cash);
		Assert.notNull(isPassed);

		Business business = cash.getBusiness();
		if (isPassed) {
			Assert.notNull(cash.getAmount());
			Assert.notNull(cash.getBusiness());
			Assert.notNull(cash.getBusiness());
			cash.setStatus(Cash.Status.approved);
		} else {
			cash.setStatus(Cash.Status.failed);
			businessService.addBalance(cash.getBusiness(), cash.getAmount(), BusinessDepositLog.Type.unfrozen, null);
		}
		businessService.addFrozenFund(business, cash.getAmount().negate());
	}

}