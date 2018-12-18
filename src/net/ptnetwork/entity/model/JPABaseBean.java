package net.ptnetwork.entity.model;

import net.ptnetwork.entity.BaseEntity;
import org.springframework.util.ClassUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Description 实体相关信息的基类
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
@MappedSuperclass
public class JPABaseBean <ID extends Serializable> extends BaseEntity<ID> {
    private static final long serialVersionUID = -707777972820275508L;

    @Column(nullable = false)
    private String name;

    private String remark;

    public String getName() {
        return name;
    }

    /**
     *名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    /**
     *显示中文
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public  Class getClassType() {
        try {
            return ClassUtils.forName(this.getClass().getName(), null);
        } catch (ClassNotFoundException | LinkageError e) {
            e.printStackTrace();
            return null;
        }
    }

}
