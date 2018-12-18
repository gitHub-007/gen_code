package noah.reflect;

import java.util.Map;
import java.util.Set;

public class EntityType<T> {
 
    public Map<String, T> map;
    
    public Set<T> set;
    
    public EntityType2<T> et;
 
    /**
     * @return the map
     */
    public Map<String, T> getMap() {
        return map;
    }
 
    /**
     * @param map the map to set
     */
    public void setMap(Map<String, T> map) {
        this.map = map;
    }
 
    /**
     * @return the set
     */
    public Set<T> getSet() {
        return set;
    }
 
    /**
     * @param set the set to set
     */
    public void setSet(Set<T> set) {
        this.set = set;
    }
 
    /**
     * @return the et
     */
    public EntityType2<T> getEt() {
        return et;
    }
 
    /**
     * @param et the et to set
     */
    public void setEt(EntityType2<T> et) {
        this.et = et;
    }
    
    
    public void testMethod(String str){
        System.out.println(str);
    }
    
}
