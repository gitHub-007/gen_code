package net.ptnetwork.entity.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-8-14
 * @Version V1.0
 */
public class FieldBean {

    private String[] fieldName;

    private String[] fieldRemark;

    private String[] javaType;

    private String[] ifShowHidden;

    private String[] ifSearchHidden;

    private String[] ifRequiredHidden;

    private String[] len;

    private String[] pointPrecision;

    private String[] defaultValue;

    private long[] entityIds;

    private String[] associationType;

    private String[] collectionType;

    private String[] ifMasterHidden;

    public static List<JPAField> toJpaFields(FieldBean fieldBean) {
        List<JPAField> jpaFields = new ArrayList<>();
        String[] fieldNameArr = fieldBean.getFieldName();
        if (fieldNameArr == null) return jpaFields;
        boolean isLenNotEmpty = ArrayUtils.isNotEmpty(fieldBean.getLen());
        boolean isPrecisionNotEmpty = ArrayUtils.isNotEmpty(fieldBean.getPointPrecision());
        boolean isRemarkNotEmpty = ArrayUtils.isNotEmpty(fieldBean.getFieldRemark());
        int fieldLen = fieldNameArr.length;
        for (int i = 0; i < fieldLen; i++) {
            JPAField field = new JPAField();
            field.setName(fieldBean.getFieldName()[i]);
            field.setJavaType(Enum.valueOf(JavaBasicTypeDic.BasicType.class, fieldBean.getJavaType()[i]).toString());
            field.setIfShow(Boolean.valueOf(fieldBean.getIfShowHidden()[i]));
            field.setIfSearch(Boolean.valueOf(fieldBean.getIfSearchHidden()[i]));
            field.setIfRequired(Boolean.valueOf(fieldBean.getIfRequiredHidden()[i]));
            if (isRemarkNotEmpty) {
                if (!StringUtils.isBlank(fieldBean.getFieldRemark()[i])) {
                    field.setRemark(fieldBean.getFieldRemark()[i].trim());
                }
            }
            if (isLenNotEmpty) {
                if (!StringUtils.isBlank(fieldBean.getLen()[i])) {
                    field.setLen(Integer.parseInt(fieldBean.getLen()[i].trim()));
                }
            }
            if (isPrecisionNotEmpty) {
                if (!StringUtils.isBlank(fieldBean.getPointPrecision()[i])) {
                    field.setPointPrecision(Integer.parseInt(fieldBean.getPointPrecision()[i].trim()));
                }
            }
            jpaFields.add(field);
        }

        return jpaFields;
    }

    public static List<EntityAssociation> toEntityAssociations(FieldBean fieldBean) {
        List<EntityAssociation> associations = new ArrayList<>();
        if (ArrayUtils.isEmpty(fieldBean.getAssociationType())) return associations;
        boolean isMasterNotEmpty = ArrayUtils.isNotEmpty(fieldBean.getIfMasterHidden());
        int typeLen = fieldBean.getAssociationType().length;
        String typeStr;
        for (int i = 0; i < typeLen; i++) {
            EntityAssociation association = new EntityAssociation();
            typeStr = fieldBean.getAssociationType()[i];
            association.setAssosciationType(Enum.valueOf(JavaBasicTypeDic.AssosationType.class, typeStr));
            association.setToEntityId(fieldBean.getEntityIds()[i]);
            JavaBasicTypeDic.AssosationType associationType = association.getAssosciationType();
            String collectionType = fieldBean.getCollectionType()[i];
            switch (associationType) {
                case OneToMany:
                case ManyToMany:
                    JavaBasicTypeDic.CollectionType collectionType1 =
                            Enum.valueOf(JavaBasicTypeDic.CollectionType.class, collectionType);
                    if (JavaBasicTypeDic.CollectionType.None != collectionType1) {
                        association.setCollectionType(collectionType1);
                    } else {
                        association.setCollectionType(JavaBasicTypeDic.CollectionType.Set);
                    }
                    break;
                default:
                    association.setCollectionType(JavaBasicTypeDic.CollectionType.None);
                    break;
            }
            if (isMasterNotEmpty) {
                association.setIsMaster(Boolean.valueOf(fieldBean.getIfMasterHidden()[i]));
            }
            associations.add(association);
        }

        return associations;
    }

    public String[] getFieldName() {
        return fieldName;
    }

    public void setFieldName(String[] fieldName) {
        this.fieldName = fieldName;
    }

    public String[] getFieldRemark() {
        return fieldRemark;
    }

    public void setFieldRemark(String[] fieldRemark) {
        this.fieldRemark = fieldRemark;
    }

    public String[] getJavaType() {
        return javaType;
    }

    public void setJavaType(String[] javaType) {
        this.javaType = javaType;
    }

    public String[] getPointPrecision() {
        return pointPrecision;
    }

    public void setPointPrecision(String[] pointPrecision) {
        this.pointPrecision = pointPrecision;
    }

    public String[] getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String[] defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String[] getLen() {
        return len;
    }

    public void setLen(String[] len) {
        this.len = len;
    }

    public String[] getIfShowHidden() {
        return ifShowHidden;
    }

    public void setIfShowHidden(String[] ifShowHidden) {
        this.ifShowHidden = ifShowHidden;
    }

    public String[] getIfSearchHidden() {
        return ifSearchHidden;
    }

    public void setIfSearchHidden(String[] ifSearchHidden) {
        this.ifSearchHidden = ifSearchHidden;
    }

    public String[] getIfRequiredHidden() {
        return ifRequiredHidden;
    }

    public void setIfRequiredHidden(String[] ifRequiredHidden) {
        this.ifRequiredHidden = ifRequiredHidden;
    }

    public void setAssociationType(String[] associationType) {
        this.associationType = associationType;
    }

    public String[] getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String[] collectionType) {
        this.collectionType = collectionType;
    }

    public String[] getAssociationType() {
        return associationType;
    }

    public long[] getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(long[] entityIds) {
        this.entityIds = entityIds;
    }

    public String[] getIfMasterHidden() {
        return ifMasterHidden;
    }

    public void setIfMasterHidden(String[] ifMasterHidden) {
        this.ifMasterHidden = ifMasterHidden;
    }
}
