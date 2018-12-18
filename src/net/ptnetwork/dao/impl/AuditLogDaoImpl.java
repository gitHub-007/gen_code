/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao.impl;

import org.springframework.stereotype.Repository;

import net.ptnetwork.dao.AuditLogDao;
import net.ptnetwork.entity.AuditLog;

/**
 * Dao - 审计日志
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class AuditLogDaoImpl extends BaseDaoImpl<AuditLog, Long> implements AuditLogDao {

	public void removeAll() {
		String jpql = "delete from AuditLog auditLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}