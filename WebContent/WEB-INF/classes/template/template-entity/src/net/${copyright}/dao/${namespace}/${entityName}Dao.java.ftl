<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign entityName = entity.name>   
<#assign entityNameLower = entityName?uncap_first>
package net.${copyright}.dao.${namespace};

import  net.${copyright}.dao.BaseDao;
import  net.${copyright}.entity.${namespace}.${entityName};


<#include "/custom.include"/>

public interface ${entityName}Dao extends BaseDao<${entityName}, Long>{

}