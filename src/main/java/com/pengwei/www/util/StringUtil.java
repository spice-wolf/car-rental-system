package com.pengwei.www.util;

/**
 * 字符串常用操作的工具类
 *
 * @author spice
 * @date 2020/12/07 11:00
 */
public class StringUtil {

    /**
     * 判断一个字符串是否为空（包括为null和为空串两种情况）
     *
     * @param str 一个要进行判断的字符串
     * @return 若str为空字符串，则返回true，否则，返回false
     */
    public static boolean isEmpty(String str) {
        // trim()方法去掉字符串的前后空格
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断一个字符串是否不为空（包括不为null和不为空串两种情况）
     *
     * @param str 一个要进行判断的字符串
     * @return 若str不为空字符串，则返回true，否则，返回false
     */
    public static boolean isNotEmpty(String str) {
        // trim()方法去掉字符串的前后空格
        if (str != null && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }
}
