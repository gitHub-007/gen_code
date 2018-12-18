/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode.impl;

import net.ptnetwork.Filter;
import net.ptnetwork.dao.gencode.EntityAssociationDao;
import net.ptnetwork.dao.gencode.JPAFieldDao;
import net.ptnetwork.dao.gencode.JPAProjectDao;
import net.ptnetwork.entity.model.FieldBean;
import net.ptnetwork.entity.model.JPAEntity;
import net.ptnetwork.service.gencode.JPAEntityService;
import net.ptnetwork.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by CodeGenerator
 *
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:25
 * @Description:JPAEntity()
 */
@Service
public class JPAEntityServiceImpl extends BaseServiceImpl<JPAEntity, Long> implements JPAEntityService {

    @Inject
    private JPAProjectDao jpaProjectDao;
    @Inject
    private JPAFieldDao jpaFieldDao;
    @Inject
    private EntityAssociationDao entityAssociationDao;

    @Override
    @Transactional
    public JPAEntity save(JPAEntity jPAEntity) {
        return super.save(jPAEntity);
    }

    @Override
    @Transactional
    public JPAEntity update(JPAEntity jPAEntity) {
        return super.update(jPAEntity);
    }

    @Override
    @Transactional
    public JPAEntity update(JPAEntity jPAEntity, String... ignoreProperties) {
        return super.update(jPAEntity, ignoreProperties);
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
    public void delete(JPAEntity jPAEntity) {
        super.delete(jPAEntity);
    }

    @Override
    @Transactional
    public void saveEntity(JPAEntity jPAEntity, Long projectId, FieldBean fieldBean) {
        jPAEntity.setJpaProject(jpaProjectDao.find(projectId));
        JPAEntity jPAEntity1 = super.save(jPAEntity);
        FieldBean.toJpaFields(fieldBean).stream().forEach(field -> {
            field.setJpaEntity(jPAEntity1);
            jpaFieldDao.persist(field);
        });
        FieldBean.toEntityAssociations(fieldBean).stream().forEach(association -> {
            association.setFromEntityId(jPAEntity1.getId());
            entityAssociationDao.persist(association);
        });
    }

    /**
     * @param jPAEntity
     * @param fieldBean
     */
    @Override
    @Transactional
    public void updateEntity(JPAEntity jPAEntity, FieldBean fieldBean) {
        JPAEntity jPAEntity1 = super.update(jPAEntity, "id", "jpaProject");
        jPAEntity1.getFieldSet().forEach(field -> jpaFieldDao.remove(field));
        FieldBean.toJpaFields(fieldBean).stream().forEach(field -> {
            field.setJpaEntity(jPAEntity1);
            jpaFieldDao.persist(field);
        });
        Filter filter = Filter.eq("fromEntityId", jPAEntity.getId());
        List<Filter> filters = Stream.of(filter).collect(Collectors.toList());
        Optional.ofNullable(entityAssociationDao.findList(0,(int)entityAssociationDao.count(filter), filters, null))
                .orElse(new ArrayList<>()).stream().forEach(association -> entityAssociationDao.remove(association));
        FieldBean.toEntityAssociations(fieldBean).stream().forEach(association -> {
            association.setFromEntityId(jPAEntity1.getId());
            entityAssociationDao.persist(association);
        });

    }

}

