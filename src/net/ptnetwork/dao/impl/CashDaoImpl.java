/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.CashDao;
import net.ptnetwork.entity.Business;
import net.ptnetwork.entity.Cash;

/**
 * Dao - 提现
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class CashDaoImpl extends BaseDaoImpl<Cash, Long> implements CashDao {

	@Override
	public Page<Cash> findPage(Business business, Pageable pageable) {
		if (business == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cash> criteriaQuery = criteriaBuilder.createQuery(Cash.class);
		Root<Cash> root = criteriaQuery.from(Cash.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("business"), business));
		return super.findPage(criteriaQuery, pageable);
	}

}