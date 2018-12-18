package noah.reflect;

import java.util.Map;
import java.util.Set;

//public class EntityType2<T extends InputStream & StringMonitorMBean> extends EntityType<T> {
public class EntityType2<T> extends EntityType<T> {

    public Map<String, T> map2;

    public Set<T> set2;

    private T[] arr;

    public Set<? extends Number> set3;

    public Set<?> set4;

    /**
     * @return the map2
     */
    public Map<String, T> getMap2() {
        return map2;
    }

    /**
     * @param map2 the map2 to set
     */
    public void setMap2(Map<String, T> map2) {
        this.map2 = map2;
    }

    /**
     * @return the arr
     */
    public T[] getArr() {
        return arr;
    }

    /**
     * @param arr the arr to set
     */
    public void setArr(T[] arr) {
        this.arr = arr;
    }

    /**
     * @return the set2
     */
    public Set<T> getSet2() {
        return set2;
    }

    /**
     * @param set2 the set2 to set
     */
    public void setSet2(Set<T> set2) {
        this.set2 = set2;
    }

    /**
     * @return the set3
     */
    public Set<? extends Number> getSet3() {
        return set3;
    }

    /**
     * @param set3 the set3 to set
     */
    public void setSet3(Set<? extends Number> set3) {
        this.set3 = set3;
    }

    /**
     * @return the set4
     */
    public Set<?> getSet4() {
        return set4;
    }

    /**
     * @param set4 the set4 to set
     */
    public void setSet4(Set<?> set4) {
        this.set4 = set4;
    }

    private void testMethod2(String str) {
        System.out.println(str);
    }

    protected void testMethod3(String str) {
        System.out.println(str);
    }

    void testMethod4(String str) {
        System.out.println(str);
    }
}
