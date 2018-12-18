<#include "/java_copyright.include">
<#assign entityName = entity.name>   
<#assign entityNameLower = entityName?uncap_first>
package net.${copyright}.service.${namespace};

import net.${copyright}.service.BaseService;
import  net.${copyright}.entity.${namespace}.${entityName};

<#include "/custom.include"/>

public interface ${entityName}Service extends BaseService<${entityName}, Long> {
	
}
