/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ptnetwork.dao.SnDao;
import net.ptnetwork.entity.Sn;
import net.ptnetwork.service.SnService;

/**
 * Service - 序列号
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class SnServiceImpl implements SnService {

	@Inject
	private SnDao snDao;

	@Transactional
	public String generate(Sn.Type type) {
		return snDao.generate(type);
	}

}