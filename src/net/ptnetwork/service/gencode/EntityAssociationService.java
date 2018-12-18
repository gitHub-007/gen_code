/*
 * Copyright(c) 2005-2018 www.ptnetwork.com All rights reserved.
 * distributed with this file and available online at
 * http://www.ptnetwork.com/
 */
package net.ptnetwork.service.gencode;

import net.ptnetwork.entity.model.EntityAssociation;
import net.ptnetwork.service.BaseService;

import java.util.List;

/**
 * Created by CodeGenerator
 *
 * @author noah
 * @version 1.0
 * @created at: 2018-09-06 09:35:50
 * @Description:EntityAssociation()
 */
public interface EntityAssociationService extends BaseService<EntityAssociation, Long> {

    List<EntityAssociation> getJpaEntity(long fromEntityId);

}
