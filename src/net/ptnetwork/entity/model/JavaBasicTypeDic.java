package net.ptnetwork.entity.model;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-8-9
 * @Version V1.0
 */
public class JavaBasicTypeDic {

    /**
     * 字段类型
     */
    public enum BasicType {
        String("java.lang.String"), Byte("java.lang.Byte"), Short("java.lang.Short"), Integer("java.lang.Integer"),
        Double("java.lang.Double"), Float("java.lang.Float"), Long("java.lang.Long"), BigDecimal("java.math.BigDecimal"),
        BigInteger("java.math.BigInteger"), Boolean("java.lang.Boolean"), Date("java.util.Date"),
        LocalDate("java.time.LocalDate"), LocalTime("java.time.LocalTime"), LocalDateTime("java.time.LocalDateTime");

        private String packageName;

        BasicType(String packageName) {
            this.packageName = packageName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(java.lang.String packageName) {
            this.packageName = packageName;
        }


        @Override
        public String toString() {
            return packageName;
        }
    }

    /**
     * 实体关系
     */
    public enum AssosationType {

        OneToOne("一对一"), OneToMany("一对多"), ManyToOne("多对一"), ManyToMany("多对多");

        private String remark;

        AssosationType(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public enum CollectionType {
        Set, List, None
    }

}
