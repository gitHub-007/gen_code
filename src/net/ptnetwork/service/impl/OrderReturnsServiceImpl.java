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
import net.ptnetwork.entity.OrderReturns;
import net.ptnetwork.entity.Sn;
import net.ptnetwork.service.OrderReturnsService;

/**
 * Service - 订单退货
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class OrderReturnsServiceImpl extends BaseServiceImpl<OrderReturns, Long> implements OrderReturnsService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public OrderReturns save(OrderReturns orderReturns) {
		Assert.notNull(orderReturns);

		orderReturns.setSn(snDao.generate(Sn.Type.orderReturns));

		return super.save(orderReturns);
	}

}