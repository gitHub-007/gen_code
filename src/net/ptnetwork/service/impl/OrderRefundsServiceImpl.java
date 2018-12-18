/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.ptnetwork.dao.SnDao;
import net.ptnetwork.entity.OrderRefunds;
import net.ptnetwork.entity.Sn;
import net.ptnetwork.service.OrderRefundsService;

/**
 * Service - 订单退款
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class OrderRefundsServiceImpl extends BaseServiceImpl<OrderRefunds, Long> implements OrderRefundsService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public OrderRefunds save(OrderRefunds orderRefunds) {
		Assert.notNull(orderRefunds);

		orderRefunds.setSn(snDao.generate(Sn.Type.orderRefunds));

		return super.save(orderRefunds);
	}

}