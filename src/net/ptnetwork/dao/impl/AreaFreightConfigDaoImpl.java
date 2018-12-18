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
import net.ptnetwork.dao.AreaFreightConfigDao;
import net.ptnetwork.entity.Area;
import net.ptnetwork.entity.AreaFreightConfig;
import net.ptnetwork.entity.ShippingMethod;
import net.ptnetwork.entity.Store;

/**
 * Dao - 地区运费配置
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class AreaFreightConfigDaoImpl extends BaseDaoImpl<AreaFreightConfig, Long> implements AreaFreightConfigDao {

	public boolean exists(ShippingMethod shippingMethod, Store store, Area area) {
		if (shippingMethod == null || store == null || area == null) {
			return false;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AreaFreightConfig> criteriaQuery = criteriaBuilder.createQuery(AreaFreightConfig.class);
		Root<AreaFreightConfig> root = criteriaQuery.from(AreaFreightConfig.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingMethod"), shippingMethod));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("area"), area));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery).size() > 0;
	}

	public boolean unique(Long id, ShippingMethod shippingMethod, Store store, Area area) {
		if (shippingMethod == null || store == null || area == null) {
			return false;
		}
		if (id != null) {
			String jpql = "select count(*) from AreaFreightConfig areaFreightConfig where areaFreightConfig.id != :id and areaFreightConfig.shippingMethod = :shippingMethod and areaFreightConfig.store = :store and areaFreightConfig.area = :area";
			Long count = entityManager.createQuery(jpql, Long.class).setParameter("id", id).setParameter("shippingMethod", shippingMethod).setParameter("store", store).setParameter("area", area).getSingleResult();
			return count <= 0;
		} else {
			String jpql = "select count(*) from AreaFreightConfig areaFreightConfig where areaFreightConfig.shippingMethod = :shippingMethod and areaFreightConfig.store = :store and areaFreightConfig.area = :area";
			Long count = entityManager.createQuery(jpql, Long.class).setParameter("shippingMethod", shippingMethod).setParameter("store", store).setParameter("area", area).getSingleResult();
			return count <= 0;
		}
	}

	public Page<AreaFreightConfig> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AreaFreightConfig> criteriaQuery = criteriaBuilder.createQuery(AreaFreightConfig.class);
		Root<AreaFreightConfig> root = criteriaQuery.from(AreaFreightConfig.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (shippingMethod != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingMethod"), shippingMethod));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
}