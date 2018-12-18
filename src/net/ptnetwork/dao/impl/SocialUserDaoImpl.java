/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.SocialUserDao;
import net.ptnetwork.entity.SocialUser;
import net.ptnetwork.entity.User;

/**
 * Dao - 社会化用户
 * 
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class SocialUserDaoImpl extends BaseDaoImpl<SocialUser, Long> implements SocialUserDao {

	public SocialUser find(String loginPluginId, String uniqueId) {
		if (StringUtils.isEmpty(loginPluginId) || StringUtils.isEmpty(uniqueId)) {
			return null;
		}
		try {
			String jpql = "select socialUser from SocialUser socialUser where socialUser.loginPluginId = :loginPluginId and socialUser.uniqueId = :uniqueId";
			return entityManager.createQuery(jpql, SocialUser.class).setParameter("loginPluginId", loginPluginId).setParameter("uniqueId", uniqueId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Page<SocialUser> findPage(User user, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SocialUser> criteriaQuery = criteriaBuilder.createQuery(SocialUser.class);
		Root<SocialUser> root = criteriaQuery.from(SocialUser.class);
		criteriaQuery.select(root);
		if (user != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}