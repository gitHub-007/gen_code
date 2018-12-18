/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode;

import net.ptnetwork.entity.model.FieldBean;
import net.ptnetwork.entity.model.JPAEntity;
import net.ptnetwork.service.BaseService;

/**
 * Created by CodeGenerator
 *
 * @author noah
 * @version 1.0
 * @created at: 2018-07-17 12:56:25
 * @Description:JPAEntity()
 */
public interface JPAEntityService extends BaseService<JPAEntity, Long> {

    void saveEntity(JPAEntity jPAEntity, Long pId, FieldBean fieldBean);

    void updateEntity(JPAEntity jPAEntity, FieldBean fieldBean);

}
