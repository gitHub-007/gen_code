/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode.impl;

import net.ptnetwork.Filter;
import net.ptnetwork.dao.gencode.EntityAssociationDao;
import net.ptnetwork.dao.gencode.JPAEntityDao;
import net.ptnetwork.entity.model.EntityAssociation;
import net.ptnetwork.entity.model.JPAEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ptnetwork.service.impl.BaseServiceImpl;
import net.ptnetwork.service.gencode.EntityAssociationService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by CodeGenerator
 * @author noah
 * @version 1.0
 * @created at: 2018-09-06 09:35:50
 * @Description:EntityAssociation()
 */
@Service
public class EntityAssociationServiceImpl extends BaseServiceImpl<EntityAssociation, Long> implements EntityAssociationService {

	@Inject
	private EntityAssociationDao entityAssociationDao;

	@Inject
	private JPAEntityDao jpaEntityDao;

	@Override
	@Transactional
	public EntityAssociation save(EntityAssociation entityAssociation) {
		return super.save(entityAssociation);
	}

	@Override
	@Transactional
	public EntityAssociation update(EntityAssociation entityAssociation) {
		return super.update(entityAssociation);
	}

	@Override
	@Transactional
	public EntityAssociation update(EntityAssociation entityAssociation, String... ignoreProperties) {
		return super.update(entityAssociation, ignoreProperties);
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
	public void delete(EntityAssociation entityAssociation) {
		super.delete(entityAssociation);
	}

	@Override
	@Transactional
	public List<EntityAssociation> getJpaEntity(long fromEntityId) {
		Filter idFilter = Filter.eq("fromEntityId", fromEntityId);
		List<Filter> filters = Stream.of(idFilter).collect(Collectors.toList());
		List<EntityAssociation> entityAssociations =
				Optional.ofNullable(super.findList((int) super.count(idFilter),
																	  filters, null)).orElse(new ArrayList<>());
		entityAssociations.stream().forEach(association -> {
			association.setJpaEntityFrom(jpaEntityDao.find(association.getFromEntityId()));
			association.setJpaEntityTo(jpaEntityDao.find(association.getToEntityId()));
		});
		return  entityAssociations;
	}



}

