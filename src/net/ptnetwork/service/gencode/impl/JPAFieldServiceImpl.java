/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode.impl;

import net.ptnetwork.dao.gencode.JPAFieldDao;
import net.ptnetwork.entity.model.JPAField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ptnetwork.service.impl.BaseServiceImpl;
import net.ptnetwork.service.gencode.JPAFieldService;

import javax.inject.Inject;

/**
 * Created by CodeGenerator
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:26
 * @Description:JPAField()
 */
@Service
public class JPAFieldServiceImpl extends BaseServiceImpl<JPAField, Long> implements JPAFieldService {

	@Inject
	private JPAFieldDao jPAFieldDao;

	@Override
	@Transactional
	public JPAField save(JPAField jPAField) {
		return super.save(jPAField);
	}

	@Override
	@Transactional
	public JPAField update(JPAField jPAField) {
		return super.update(jPAField);
	}

	@Override
	@Transactional
	public JPAField update(JPAField jPAField, String... ignoreProperties) {
		return super.update(jPAField, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(JPAField jPAField) {
		super.delete(jPAField);
	}

}

