/*
 * Copyright 2005-2017 ptnetwork.net. All rights reserved.
 * Support: http://www.ptnetwork.net
 * License: http://www.ptnetwork.net/license
 */
package net.ptnetwork.dao;

import java.util.Date;

import net.ptnetwork.Page;
import net.ptnetwork.Pageable;
import net.ptnetwork.entity.Member;
import net.ptnetwork.entity.MemberAttribute;

/**
 * Dao - 会员
 * 
 * @author PTNETWORK Team
 * @version 5.0
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 查找会员分页
	 * 
	 * @param rankingType
	 *            排名类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType, Pageable pageable);

	/**
	 * 查询会员注册数
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员注册数
	 */
	Long registerMemberCount(Date beginDate, Date endDate);

	/**
	 * 清除会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	void clearAttributeValue(MemberAttribute memberAttribute);

}