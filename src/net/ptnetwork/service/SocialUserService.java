/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.service;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.SocialUser;
import net.ptnetwork.entity.User;

/**
 * Service - 社会化用户
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface SocialUserService extends BaseService<SocialUser, Long> {

	/**
	 * 查找社会化用户
	 * 
	 * @param loginPluginId
	 *            登录插件ID
	 * @param uniqueId
	 *            唯一ID
	 * @return 社会化用户，若不存在则返回null
	 */
	SocialUser find(String loginPluginId, String uniqueId);

	/**
	 * 查找社会化用户分页
	 * 
	 * @param user
	 *            用户
	 * @param pageable
	 *            分页信息
	 * @return 社会化用户分页
	 */
	Page<SocialUser> findPage(User user, Pageable pageable);

	/**
	 * 绑定用户
	 * 
	 * @param user
	 *            用户
	 * @param socialUser
	 *            社会化用户
	 * @param uniqueId
	 *            唯一ID
	 */
	void bindUser(User user, SocialUser socialUser, String uniqueId);

}