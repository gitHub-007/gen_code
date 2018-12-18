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
import net.ptnetwork.dao.StoreAdImageDao;
import net.ptnetwork.entity.Store;
import net.ptnetwork.entity.StoreAdImage;

/**
 * Dao - 店铺广告图片
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class StoreAdImageDaoImpl extends BaseDaoImpl<StoreAdImage, Long> implements StoreAdImageDao {

	public Page<StoreAdImage> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreAdImage> criteriaQuery = criteriaBuilder.createQuery(StoreAdImage.class);
		Root<StoreAdImage> root = criteriaQuery.from(StoreAdImage.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}