package net.ptnetwork.entity.model;

import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.typemapping.JavaImport;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 实体
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
@Entity
public class JPAEntity extends JPABaseBean<Long> {

    private String filePath;

    private String tableName;

    private String createUser;

    @ManyToOne(optional = false)
    private JPAProject jpaProject;

    @OneToMany(mappedBy = "jpaEntity", cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
    private List<JPAField> fieldSet = new ArrayList<>();

    @Transient
    List<EntityAssociation> entityAssociations = new ArrayList<>();

    /**
     * 获取实体名称，用于文件夹生成
     *
     * @return
     */
    @Transient
    public String getEntityName() {
        return this.getName();
    }

    /**
     * 获取搜索字段(页面)
     */
    @Transient
    public List<JPAField> getSearchFields() {
        return fieldSet.stream().filter(field -> field.getIfSearch()).collect(Collectors.toList());
    }

    /**
     * 获取显示字段(页面[add.ftl,edit.ftl])
     */
    @Transient
    public List<JPAField> getShowFields() {
        return fieldSet.stream().filter(field -> field.getIfShow()).collect(Collectors.toList());
    }

    @Transient
    public List<JPAField> getRequiredFields() {
        return fieldSet.stream().filter(field -> field.getIfShow() && field.getIfRequired()).collect(Collectors.toList());
    }

    @Transient
    public boolean getTableNameExsit() {
        return !StringUtils.isBlank(tableName);
    }

    @Transient
    public String getUnderCrossName() {
        return StringHelper.toUnderscoreName(this.getName());
    }

    /**
     * 获取需要导入的类
     *
     * @return
     */
    @Transient
    public Set<String> getImportClasses() {
        JavaImport javaImports = new JavaImport();
        fieldSet.stream().forEach(field -> {
            field.getAnnotations().stream().forEach(annotation -> javaImports.addImport(annotation.getClassType().getName()));
            javaImports.addImport(field.getPackageName());
        });
        entityAssociations.stream().filter(filter -> filter.getCollectionType() == JavaBasicTypeDic.CollectionType.Set || filter.getCollectionType() == JavaBasicTypeDic.CollectionType.List).forEach(entityAssociation -> {
            switch (entityAssociation.getCollectionType()) {
                case Set:
                    javaImports.addImport(Set.class.getName());
                    javaImports.addImport(HashSet.class.getName());
                    break;
                case List:
                    javaImports.addImport(List.class.getName());
                    javaImports.addImport(ArrayList.class.getName());
                    break;
            }
        });
        return javaImports.getImports();
    }

    public List<JPAField> getFieldSet() {
        return fieldSet;
    }

    public void setFieldSet(List<JPAField> fieldSet) {
        this.fieldSet = fieldSet;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public JPAProject getJpaProject() {
        return jpaProject;
    }

    public void setJpaProject(JPAProject jpaProject) {
        this.jpaProject = jpaProject;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<EntityAssociation> getEntityAssociations() {
        return entityAssociations;
    }

    public void setEntityAssociations(List<EntityAssociation> entityAssociations) {
        this.entityAssociations = entityAssociations;
    }
}
