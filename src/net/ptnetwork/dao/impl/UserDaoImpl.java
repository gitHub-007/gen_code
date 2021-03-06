/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao.impl;

import org.springframework.stereotype.Repository;

import net.ptnetwork.dao.UserDao;
import net.ptnetwork.entity.User;

/**
 * Dao - 用户
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

}