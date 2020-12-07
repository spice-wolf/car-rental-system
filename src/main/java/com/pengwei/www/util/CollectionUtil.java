package com.pengwei.www.util;

import java.util.Collection;

/**
 * 集合常用操作的工具类
 *
 * @author spice
 * @date 2020/12/07 11:44
 */
public class CollectionUtil {

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 为空则返回true，否则，返回false
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断集合是否不为空
     *
     * @param collection 集合
     * @return 不为空则返回true，否则，返回false
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
