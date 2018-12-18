<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign entityName = entity.name>   
<#assign entityNameLower = entityName?uncap_first>
package net.${copyright}.dao.${namespace}.impl;

import org.springframework.stereotype.Repository;
import net.${copyright}.dao.impl.BaseDaoImpl;
import net.${copyright}.dao.${namespace}.${entityName}Dao;
import net.${copyright}.entity.${namespace}.${entityName};

<#include "/custom.include"/>

@Repository
public class ${entityName}DaoImpl extends BaseDaoImpl<${entityName}, Long> implements ${entityName}Dao {
    
}