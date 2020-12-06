package com.pengwei.www.orm.util;

/**
 * 与命名风格转化相关的工具类
 *
 * @author spice
 * @date 2020/12/06 21:51
 */
public class NameUtil {

    /**
     * 将【以下划线区分单词】风格的名字转化为【驼峰命名法并且首字母大写】风格的名字
     * 例如：user_name将被转换为UserName
     *
     * @param name 【以下划线区分单词】风格的名字
     * @return 【驼峰命名法并且首字母大写】风格的名字
     */
    public static String methodNameConvertor(String name) {
        char[] ch = name.toCharArray();
        // 将字符串的首字母转换为大写
        ch[0] -= 32;

        return camelCaseConvertor(String.valueOf(ch));
    }

    /**
     * 将【以下划线区分单词】风格的名字转化为【驼峰命名法】风格的名字
     * 例如：user_name将被转换为userName
     *
     * @param name 【以下划线区分单词】风格的名字
     * @return 【驼峰命名法】风格的名字
     */
    public static String camelCaseConvertor(String name) {
        int index;
        StringBuilder str = new StringBuilder(name);

        // 遍历寻找字符串中下划线（"_"）所在的所有位置
        // 若不存在下划线，则str.indexOf("_"))将返回-1
        while ((index = str.indexOf("_")) != -1) {
            // 将下划线后一位字母转换为大写
            str.setCharAt(index + 1, (char) (str.charAt(index + 1) - 32));
            // 移除下划线
            str.replace(index, index + 1, "");
        }

        return str.toString();
    }
}
