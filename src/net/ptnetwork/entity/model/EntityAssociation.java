package net.ptnetwork.entity.model;

import net.ptnetwork.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-9-5
 * @Version V1.0
 */
@Entity
public class EntityAssociation extends BaseEntity<Long> {

    private static final long serialVersionUID = -9179955693916620666L;

    @Column(nullable = false)
    private Long fromEntityId;

    @Column(nullable = false)
    private Long  toEntityId;

    @Transient
    private JPAEntity  jpaEntityTo;

    @Transient
    private JPAEntity  jpaEntityFrom;

    @Column(nullable = false)
    private JavaBasicTypeDic.AssosationType assosciationType;

    private JavaBasicTypeDic.CollectionType collectionType;

    @Column(nullable = false)
    private boolean  isMaster;

    public Long getFromEntityId() {
        return fromEntityId;
    }

    public void setFromEntityId(Long fromEntityId) {
        this.fromEntityId = fromEntityId;
    }

    public Long getToEntityId() {
        return toEntityId;
    }

    public void setToEntityId(Long toEntityId) {
        this.toEntityId = toEntityId;
    }

    public JavaBasicTypeDic.CollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(JavaBasicTypeDic.CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    public JavaBasicTypeDic.AssosationType getAssosciationType() {
        return assosciationType;
    }

    public void setAssosciationType(JavaBasicTypeDic.AssosationType assosciationType) {
        this.assosciationType = assosciationType;
    }

    public JPAEntity getJpaEntityTo() {
        return jpaEntityTo;
    }

    public void setJpaEntityTo(JPAEntity jpaEntityTo) {
        this.jpaEntityTo = jpaEntityTo;
    }

    public boolean getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public JPAEntity getJpaEntityFrom() {
        return jpaEntityFrom;
    }

    public void setJpaEntityFrom(JPAEntity jpaEntityFrom) {
        this.jpaEntityFrom = jpaEntityFrom;
    }
}
