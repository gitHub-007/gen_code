/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.ptnetwork.dao.AuditLogDao;
import net.ptnetwork.entity.AuditLog;
import net.ptnetwork.service.AuditLogService;

/**
 * Service - 审计日志
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLog, Long> implements AuditLogService {

	@Inject
	private AuditLogDao auditLogDao;

	@Async
	public void create(AuditLog auditLog) {
		auditLogDao.persist(auditLog);
	}

	public void clear() {
		auditLogDao.removeAll();
	}

}