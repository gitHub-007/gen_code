package net.ptnetwork.entity.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-7-17
 * @Version V1.0
 */
@Entity
public class JPAProject extends JPABaseBean<Long> {

    private String projectPath;

    private String copyright;

    @OneToMany(mappedBy = "jpaProject",cascade = {CascadeType.REMOVE,CascadeType.REFRESH})
    private Set<JPAEntity> jpaEntities=new HashSet<>();

    public Set<JPAEntity> getJpaEntities() {
        return jpaEntities;
    }

    public void setJpaEntities(Set<JPAEntity> jpaEntities) {
        this.jpaEntities = jpaEntities;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
