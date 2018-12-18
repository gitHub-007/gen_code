/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.ptnetwork.dao.OrderPaymentDao;
import net.ptnetwork.dao.SnDao;
import net.ptnetwork.entity.OrderPayment;
import net.ptnetwork.entity.Sn;
import net.ptnetwork.service.OrderPaymentService;

/**
 * Service - 订单支付
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPayment, Long> implements OrderPaymentService {

	@Inject
	private OrderPaymentDao orderPaymentDao;
	@Inject
	private SnDao snDao;

	@Transactional(readOnly = true)
	public OrderPayment findBySn(String sn) {
		return orderPaymentDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional
	public OrderPayment save(OrderPayment orderPayment) {
		Assert.notNull(orderPayment);

		orderPayment.setSn(snDao.generate(Sn.Type.orderPayment));

		return super.save(orderPayment);
	}

}