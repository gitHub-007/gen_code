/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode.impl;

import net.ptnetwork.dao.gencode.JPAFieldAnnotationDao;
import net.ptnetwork.entity.model.JPAFieldAnnotation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ptnetwork.service.impl.BaseServiceImpl;
import net.ptnetwork.service.gencode.JPAFieldAnnotationService;

import javax.inject.Inject;

/**
 * Created by CodeGenerator
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:26
 * @Description:JPAFieldAnnotation()
 */
@Service
public class JPAFieldAnnotationServiceImpl extends BaseServiceImpl<JPAFieldAnnotation, Long> implements JPAFieldAnnotationService {

	@Inject
	private JPAFieldAnnotationDao jPAFieldAnnotationDao;

	@Override
	@Transactional
	public JPAFieldAnnotation save(JPAFieldAnnotation jPAFieldAnnotation) {
		return super.save(jPAFieldAnnotation);
	}

	@Override
	@Transactional
	public JPAFieldAnnotation update(JPAFieldAnnotation jPAFieldAnnotation) {
		return super.update(jPAFieldAnnotation);
	}

	@Override
	@Transactional
	public JPAFieldAnnotation update(JPAFieldAnnotation jPAFieldAnnotation, String... ignoreProperties) {
		return super.update(jPAFieldAnnotation, ignoreProperties);
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
	public void delete(JPAFieldAnnotation jPAFieldAnnotation) {
		super.delete(jPAFieldAnnotation);
	}

}

