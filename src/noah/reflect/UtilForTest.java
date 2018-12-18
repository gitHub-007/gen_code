package noah.reflect;

import java.util.*;
import java.util.Map.Entry;

public class UtilForTest {
 
    /**
     * 
     * 功能描述: <br>
     * 遍历显示数组，末尾会加一个空白行
     *
     * @param desc  每行数组的统一注释
     * @param arr   数组对象
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> void ergodicDisplayArray(String desc, T[] arr) {
        if (arr.length == 0) {
            System.out.println(desc + "数组长度为0");
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println("Array:--" + desc + arr[i]);
        }
        System.out.println();
    }
    /**
     * 
     * 功能描述: <br>
     * 显示字符串并加一个空白行
     *
     * @param str
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> void DisplayStr(String str) {
            System.out.println("String:--" + str);
            System.out.println();
    }
    
    /**
     * 
     * 功能描述: <br>
     * 遍历显示集合，末尾会加一个空白行
     *
     * @param desc  每行数组的统一注释
     * @param arr   数组对象
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> void ergodicDisplayCollection(Collection<T> collection) {
        if (collection instanceof List || collection instanceof Set) {
            for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
                T t = (T) iterator.next();
                System.out.println(t);
            }
        }
        System.out.println();
    }
    
    /**
     * 
     * 功能描述: <br>
     * 遍历显示集合，末尾会加一个空白行
     *
     * @param desc  每行数组的统一注释
     * @param arr   数组对象
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <K, V> void ergodicDisplayMap(Map<K, V> map) {
        for (Entry<K, V> e : map.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        System.out.println();
    }
    
    /**
     * 
     * 功能描述: <br>
     * 遍历显示集合，末尾会加一个空白行
     *
     * @param desc  每行数组的统一注释
     * @param arr   数组对象
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T, K, V> void ergodicDisplay(String desc, Object obj) {
        System.out.println(desc);
        try {
            if (obj instanceof Collection<?>) {
                ergodicDisplayCollection((Collection<?>) obj);
            }
            if (obj instanceof Map<?, ?>) {
                ergodicDisplayMap((Map<?, ?>) obj);
            }
            if (obj.getClass().isArray()) {
                ergodicDisplayArray("",(T[]) obj);
            }
        } catch (Exception e) {
            System.out.println(desc + "格式错误，请确定格式");
        }
    }
}
