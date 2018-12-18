/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode.impl;

import net.ptnetwork.dao.gencode.JPAProjectDao;
import net.ptnetwork.entity.model.JPAProject;
import net.ptnetwork.service.gencode.JPAProjectService;
import net.ptnetwork.service.impl.BaseServiceImpl;
import org.drools.examples.HelloWorldExample;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by CodeGenerator
 *
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 17:32:28
 * @Description:JPAProject()
 */
@Service
public class JPAProjectServiceImpl extends BaseServiceImpl<JPAProject, Long> implements JPAProjectService {
    private static final Logger logger = LoggerFactory.getLogger(JPAProjectServiceImpl.class);

    @Inject
    private JPAProjectDao jPAProjectDao;


    @Override
    @Transactional
    public JPAProject save(JPAProject jPAProject) {
        return super.save(jPAProject);
    }

    @Override
    @Transactional
    public JPAProject update(JPAProject jPAProject) {
        return super.update(jPAProject);
    }

    @Override
    @Transactional
    public JPAProject update(JPAProject jPAProject, String... ignoreProperties) {
        return super.update(jPAProject, ignoreProperties);
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
    public void delete(JPAProject jPAProject) {
        super.delete(jPAProject);
    }

}

