<#include "/java_copyright.include">
<#assign entityName = entity.name>   
<#assign entityNameLower = entityName?uncap_first>
package net.${copyright}.service.${namespace}.impl;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.${copyright}.service.impl.BaseServiceImpl;
import net.${copyright}.service.${namespace}.${entityName}Service;
import net.${copyright}.dao.${namespace}.${entityName}Dao;
import net.${copyright}.entity.${namespace}.${entityName};

<#include "/custom.include"/>

@Service
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}, Long> implements ${entityName}Service {

	private static final Logger logger = LoggerFactory.getLogger(${entityName}.class);

	@Inject
	private ${entityName}Dao ${entityNameLower}Dao;

	@Override
	@Transactional
	public ${entityName} save(${entityName} ${entityNameLower}) {
		return super.save(${entityNameLower});
	}

	@Override
	@Transactional
	public ${entityName} update(${entityName} ${entityNameLower}) {
		return super.update(${entityNameLower});
	}

	@Override
	@Transactional
	public ${entityName} update(${entityName} ${entityNameLower}, String... ignoreProperties) {
		return super.update(${entityNameLower}, ignoreProperties);
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
	public void delete(${entityName} ${entityNameLower}) {
		super.delete(${entityNameLower});
	}

}

