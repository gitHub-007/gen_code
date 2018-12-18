/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.event;

import org.springframework.context.ApplicationEvent;

import net.ptnetwork.entity.User;

/**
 * Event - 用户
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public abstract class  UserEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7432438231982667326L;

	/**
	 * 用户
	 */
	private User user;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserEvent(Object source, User user) {
		super(source);
		this.user = user;
	}

	/**
	 * 获取用户
	 * 
	 * @return 用户
	 */
	public User getUser() {
		return user;
	}

}