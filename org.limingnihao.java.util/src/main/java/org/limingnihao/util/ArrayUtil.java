package org.limingnihao.util;

/**
 * 数组工具类
 *
 * @author thecode
 */
public class ArrayUtil {

    public static boolean isBlank(Object[] array) {
        if (array != null && array.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(Object[] array) {
        if (array != null && array.length > 0) {
            return true;
        } else {
            return false;
        }
    }
}
