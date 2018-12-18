/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.InstantMessageDao;
import net.ptnetwork.entity.InstantMessage;
import net.ptnetwork.entity.Store;
import net.ptnetwork.service.InstantMessageService;

/**
 * Service - 即时通讯
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class InstantMessageServiceImpl extends BaseServiceImpl<InstantMessage, Long> implements InstantMessageService {

	@Inject
	private InstantMessageDao instantMessageDao;

	@Transactional(readOnly = true)
	public Page<InstantMessage> findPage(Store store, Pageable pageable) {
		return instantMessageDao.findPage(store, pageable);
	}

}