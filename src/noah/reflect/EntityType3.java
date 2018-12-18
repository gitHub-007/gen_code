package noah.reflect;

import java.util.ArrayList;
import java.util.List;

public class EntityType3 extends EntityType2<Long> {
 
 
    private List<List<String>> a = new ArrayList<>();
    
    private Object o;
 
 
    /**
     * @return the a
     */
    public List<List<String>> getA() {
        return a;
    }
 
 
    /**
     * @param a the a to set
     */
    public void setA(List<List<String>> a) {
        this.a = a;
    }
 
 
    /**
     * @return the o
     */
    public Object getO() {
        return o;
    }
 
 
    /**
     * @param o the o to set
     */
    public void setO(Object o) {
        this.o = o;
    }
}
