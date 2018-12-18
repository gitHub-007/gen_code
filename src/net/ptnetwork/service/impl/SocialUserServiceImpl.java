/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.dao.SocialUserDao;
import net.ptnetwork.entity.SocialUser;
import net.ptnetwork.entity.User;
import net.ptnetwork.service.SocialUserService;

/**
 * Service - 社会化用户
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Service
public class SocialUserServiceImpl extends BaseServiceImpl<SocialUser, Long> implements SocialUserService {

	@Inject
	private SocialUserDao socialUserDao;

	@Transactional(readOnly = true)
	public SocialUser find(String loginPluginId, String uniqueId) {
		return socialUserDao.find(loginPluginId, uniqueId);
	}

	@Transactional(readOnly = true)
	public Page<SocialUser> findPage(User user, Pageable pageable) {
		return socialUserDao.findPage(user, pageable);
	}

	public void bindUser(User user, SocialUser socialUser, String uniqueId) {
		Assert.notNull(socialUser);
		Assert.hasText(uniqueId);

		if (!socialUser.getUniqueId().equals(uniqueId) || socialUser.getUser() != null) {
			return;
		}

		socialUser.setUser(user);
	}

}