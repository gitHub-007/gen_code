/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.event;

import net.ptnetwork.entity.Cart;

/**
 * Event - 清空购物车SKU
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public class CartClearedEvent extends CartEvent {

	private static final long serialVersionUID = -5881246837387897341L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartClearedEvent(Object source, Cart cart) {
		super(source, cart);
	}

}