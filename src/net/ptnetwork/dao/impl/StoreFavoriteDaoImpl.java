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

import net.ptnetwork.Filter;
import net.ptnetwork.Order;
import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.StoreFavoriteDao;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.Store;
import net.ptnetwork.entity.StoreFavorite;

/**
 * Dao - 店铺收藏
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class StoreFavoriteDaoImpl extends BaseDaoImpl<StoreFavorite, Long> implements StoreFavoriteDao {

	public boolean exists(Member member, Store store) {
		String jpql = "select count(*) from StoreFavorite storeFavorite where storeFavorite.member = :member and storeFavorite.store = :store";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("member", member).setParameter("store", store).getSingleResult();
		return count > 0;
	}

	public List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreFavorite> criteriaQuery = criteriaBuilder.createQuery(StoreFavorite.class);
		Root<StoreFavorite> root = criteriaQuery.from(StoreFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<StoreFavorite> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreFavorite> criteriaQuery = criteriaBuilder.createQuery(StoreFavorite.class);
		Root<StoreFavorite> root = criteriaQuery.from(StoreFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreFavorite> criteriaQuery = criteriaBuilder.createQuery(StoreFavorite.class);
		Root<StoreFavorite> root = criteriaQuery.from(StoreFavorite.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery);
	}

}