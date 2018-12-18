/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.StockLogDao;
import net.ptnetwork.entity.StockLog;
import net.ptnetwork.entity.Store;

/**
 * Dao - 库存记录
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class StockLogDaoImpl extends BaseDaoImpl<StockLog, Long> implements StockLogDao {

	public Page<StockLog> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StockLog> criteriaQuery = criteriaBuilder.createQuery(StockLog.class);
		Root<StockLog> root = criteriaQuery.from(StockLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sku").get("product").get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}