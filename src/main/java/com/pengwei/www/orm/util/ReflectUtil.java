package com.pengwei.www.orm.util;

import java.lang.reflect.Method;

/**
 * 反射常用操作的工具类
 *
 * @author spice
 * @date 2020/12/06 23:38
 */
public class ReflectUtil {

    /**
     * 通过反射，执行指定对象的指定成员变量的getter方法，从而获取该成员变量的值。
     * 注：该成员变量通过数据库字段名映射而来
     *
     * @param obj 要进行操作的对象（通常为po类对象）
     * @param columnName 一个数据库字段名
     * @return 该成员变量的值
     */
    public static Object invokeGetter(Object obj, String columnName) {
        Class cls = obj.getClass();
        // 获取变量的getter方法的名字，例如：getUserId
        StringBuilder methodName = new StringBuilder("get").append(NameUtil.methodNameConvertor(columnName));

        Object result = null;
        try {
            // 获取getter方法
            Method method = cls.getDeclaredMethod(methodName.toString(), null);
            // 执行getter方法，获取值并返回
            result = method.invoke(obj, null);
        } catch (Exception e) {
            System.out.println("程序出现错误");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 通过反射，执行指定对象的指定成员变量的setter方法，从而该指定成员变量赋值
     * 注：通过数据库字段名映射而来
     *
     * @param obj 要进行操作的对象(通常为po类对象)
     * @param columnName 一个数据库字段名
     * @param columnValue 该字段的值
     */
    public static void invokeSetter(Object obj, String columnName, Object columnValue) {
        Class cls = obj.getClass();
        // 获取变量的setter方法的名字，如setUserId
        StringBuilder methodName = new StringBuilder("set").append(NameUtil.methodNameConvertor(columnName));

        try {
            // 获取setter方法
            Method method = cls.getDeclaredMethod(methodName.toString(), columnValue.getClass());
            // 执行setter方法，将数据存储到对象中
            method.invoke(obj, columnValue);
        } catch (Exception e) {
            System.out.println("程序出现错误");
            e.printStackTrace();
        }
    }
}
