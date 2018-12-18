package net.ptnetwork.entity.model;

import cn.org.rapid_framework.generator.util.typemapping.JavaPrimitiveTypeMapping;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Description 属性
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
@Entity
public class JPAField extends JPABaseBean<Long> {

    // 类型全路径（eg:java.lang.String）
    private String javaType;

    private boolean ifShow = true;

    private boolean ifSearch;

    private boolean ifRequired = true;

//    private boolean isNullAble = true;

    private Integer len;

    private Integer pointPrecision;

    private String defaultValue;

    @ManyToOne(optional = false)
    private JPAEntity jpaEntity;

    @OneToMany(mappedBy = "jpaField", cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    private List<JPAFieldAnnotation> annotations = new ArrayList<>();

    public List<JPAFieldAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<JPAFieldAnnotation> annotations) {
        this.annotations = annotations;
    }

    public boolean getIfShow() {
        return ifShow;
    }

    /**
     * 是否显示(页面)
     */
    public void setIfShow(boolean ifShow) {
        this.ifShow = ifShow;
    }

    public boolean getIfSearch() {
        return ifSearch;
    }

    /**
     * 是否搜索条件(页面)
     */
    public void setIfSearch(boolean ifSearch) {
        this.ifSearch = ifSearch;
    }

    public boolean getIfRequired() {
        return ifRequired;
    }

    /**
     * 是否必填(页面)
     */
    public void setIfRequired(boolean ifRequired) {
        this.ifRequired = ifRequired;
    }

    /**
     * 获取完整的包名（eg:java.lang.String）
     *
     * @return
     */
    public String getPackageName() {
        return JavaPrimitiveTypeMapping.getWrapperClass(getClassType()).getName();
    }

    public JPAEntity getJpaEntity() {
        return jpaEntity;
    }

    public void setJpaEntity(JPAEntity jpaEntity) {
        this.jpaEntity = jpaEntity;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getPointPrecision() {
        return pointPrecision;
    }

    public void setPointPrecision(int pointPrecision) {
        this.pointPrecision = pointPrecision;
    }

    @Transient
    public String getShortTypeName() {
        return StringUtils.substringAfterLast(javaType, ".");
    }

    /**
     * 获取属性的类型（eg:String ,HashSet等）
     *
     * @return
     */
    @Transient
    public String getFieldJavaType() {
        return JavaPrimitiveTypeMapping.getWrapperType(getClassType().getSimpleName());
    }

    @Override
    @Transient
    public Class getClassType() {
        try {
            return ClassUtils.forName(javaType, null);
        } catch (ClassNotFoundException | LinkageError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    @Transient
    public String getColunmDesc() {
        StringJoiner stringJoiner = new StringJoiner(",");
        JavaBasicTypeDic.BasicType basicType =
                JavaBasicTypeDic.BasicType.valueOf(StringUtils.substringAfterLast(javaType, "."));
        if (len != null && len > 0) {
            switch (basicType) {
                case Double:
                case Float:
                case BigDecimal:
                    stringJoiner.add("precision=" + len);
                    break;
                default:
                    stringJoiner.add("length=" + len);

            }

        }
        if (pointPrecision != null && pointPrecision > 0) {
            stringJoiner.add("scale=" + pointPrecision);
        }
        stringJoiner.add("nullable=" + (ifRequired ? false : true));
        return stringJoiner.toString();
    }

}
