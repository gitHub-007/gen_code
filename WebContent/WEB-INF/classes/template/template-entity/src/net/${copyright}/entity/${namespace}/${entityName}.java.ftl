<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign entityName = entity.name>
<#assign entityNameLower = entityName?uncap_first>
package net.${copyright}.entity.${namespace};

import javax.persistence.*;
import net.${copyright}.entity.BaseEntity;
<#list entity.importClasses as importClass>
import ${importClass};
</#list>

<#include "/custom.include"/>

@Entity
<#if entity.tableNameExsit>
@Table(name="${entity.tableName}")
<#else>
@Table(name="${entity.underCrossName}")
</#if>
public class ${entityName} extends BaseEntity<Long> {

	<#-- 输出字段 -->
	<#list entity.fieldSet as field>
	//${field.remark!field.name}
	<#list field.annotations as annotation>
	@${annotation.simpleName}
	</#list>
    @Column(${field.colunmDesc})
	private ${field.fieldJavaType} ${field.name};

	</#list>

	<#-- 关系-->
    <#list entity.entityAssociations as entityAssociation>
    <#assign toEntityName = entityAssociation.jpaEntityTo.name>
    <#assign fromEntityName = entityAssociation.jpaEntityFrom.name>
	<#assign associationName = entityAssociation.assosciationType.name()>
	@${associationName}<@compress single_line=true>
	<#switch associationName>
		<#case "OneToOne">
		<#case "OneToMany">
		<#case "ManyToMany">
		<#if entityAssociation.isMaster>(mappedBy="${fromEntityName?uncap_first}",cascade = {CascadeType.REMOVE,CascadeType.REFRESH})</#if>
		<#break>
		<#case "ManyToOne">
		(optional=false)
		<#break>
	</#switch>
	</@compress>

    private <@compress single_line=true>
	<#if associationName=="OneToMany" || associationName=="ManyToMany">
		<#switch entityAssociation.collectionType.name()>
			<#case "List">
        		List<${toEntityName}>
				<#break>
			<#case "Set">
        		 Set<${toEntityName}>
				<#break>
		</#switch>
	<#else>
	${toEntityName}
	</#if>
	</@compress>
    <#if associationName=="OneToMany" || associationName=="ManyToMany">
	${toEntityName?uncap_first}s = new <@compress single_line=true>
		<#switch entityAssociation.collectionType.name()>
			<#case "List">
        		ArrayList<>();
			<#break>
			<#case "Set">
        		HashSet<>();
			<#break>
		</#switch>
		</@compress>
	<#else>
	${toEntityName?uncap_first};
	</#if>


    </#list>

	<#-- 生成字段的get,set方法-->
	<#list entity.fieldSet as field>
	<#assign propertyName = field.name/>
	<#assign javaType = field.fieldJavaType/>
	<#assign remark = field.remark!propertyName/>
	<@genGetterAndSetter propertyName javaType remark/>
	</#list>

	<#-- 生成关系的get,set方法-->
	<#list entity.entityAssociations as entityAssociation>
	<#assign javaType = entityAssociation.jpaEntityTo.name>
	<#assign entityName = entityAssociation.jpaEntityTo.name>
	<#assign associationName = entityAssociation.assosciationType.name()>
	<#if associationName=="OneToMany" || associationName=="ManyToMany">
	<#assign entityName = entityName+"s">
		<#switch entityAssociation.collectionType.name()>
			<#case "List">
				<#assign javaType = "List<"+javaType+">">
				<#break>
			<#case "Set">
        		<#assign javaType = "Set<"+javaType+">">
				<#break>
		</#switch>
	</#if>
	<@genGetterAndSetter entityName?uncap_first javaType entityName/>
	</#list>

	<#-- 生成get set方法-->
	<#macro genGetterAndSetter propertyName javaType remark>
	public ${javaType} get${propertyName?cap_first}() {
		return ${propertyName};
	}

	/**
	 * ${remark!""}
	 */
	public void set${propertyName?cap_first}(${javaType} ${propertyName}) {
		this.${propertyName} = ${propertyName};
	}

	</#macro>
}
