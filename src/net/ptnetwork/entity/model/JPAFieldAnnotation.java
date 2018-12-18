package net.ptnetwork.entity.model;

import net.ptnetwork.BaseAttributeConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description 属性对应的注解
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
@Entity
public class JPAFieldAnnotation extends JPABaseBean<Long> {

    private static final String JPA_PACKAGE = "javax.persistence";

    private String annotationName;

    private String annotationPackage;

    @Column(length = 4000)
    @Convert(converter = ParamsConverter.class)
    private Map<String, Object> annotationParams = new HashMap<>();

    @ManyToOne(optional = false)
    private JPAField jpaField;

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public Map<String, Object> getAnnotationParamsJson() {
        return annotationParams;
    }

    public void setAnnotationParams(Map<String, Object> annotationParams) {
        this.annotationParams = annotationParams;
    }

    public JPAField getJpaField() {
        return jpaField;
    }

    public void setJpaField(JPAField jpaField) {
        this.jpaField = jpaField;
    }

    @Transient
    public String getSimpleName() {
        return getClassType().getSimpleName();
    }

    public String getAnnotationPackage() {
        return annotationPackage;
    }

    public void setAnnotationPackage(String annotationPackage) {
        this.annotationPackage = annotationPackage;
    }


    @Override
    @Transient
    public Class getClassType() {
        try {
            return ClassUtils.forName(String.format("%s.%s",annotationPackage,annotationName) ,null);
        } catch (ClassNotFoundException | LinkageError e) {
            e.printStackTrace();
        }
        return null;
    }

    @PrePersist
    public void prePersist() {
        this.setAnnotationPackage(StringUtils.isBlank(this.annotationPackage) ? JPA_PACKAGE : this.annotationPackage);
    }

    @Converter
    public static class ParamsConverter extends BaseAttributeConverter<Map<String, Object>> {
    }

}
