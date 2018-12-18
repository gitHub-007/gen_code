/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.ptnetwork.Order;
import net.ptnetwork.dao.SvcDao;
import net.ptnetwork.entity.Store;
import net.ptnetwork.entity.StoreRank;
import net.ptnetwork.entity.Svc;

/**
 * Dao - 服务
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class SvcDaoImpl extends BaseDaoImpl<Svc, Long> implements SvcDao {

	public List<Svc> find(Store store, String promotionPluginId, StoreRank storeRank, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Svc> criteriaQuery = criteriaBuilder.createQuery(Svc.class);
		Root<Svc> root = criteriaQuery.from(Svc.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (promotionPluginId != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotionPluginId"), promotionPluginId));
		}
		if (storeRank != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("storeRank"), storeRank));
		}
		criteriaQuery.where(restrictions);
		if (orders == null || orders.isEmpty()) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
		}
		return super.findList(criteriaQuery, null, null, null, orders);
	}

}