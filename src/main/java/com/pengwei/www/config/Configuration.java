package com.pengwei.www.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类
 *
 * @author spice
 * @date 2020/12/06 17:49
 */
public class Configuration {

    /**
     * 数据库配置文件的名称
     */
    private static final String PROP_NAME = "db.properties";

    /**
     * 数据库连接的驱动名
     * 默认为：com.mysql.jdbc.Driver
     */
    private static final String JDBC_NAME;

    /**
     * 访问数据库所用的url
     * 默认为：jdbc:mysql://localhost:3306/car_rental_system?useSSL=false&useUnicode=true&characterEncoding=utf8
     */
    private static final String DB_URL;

    /**
     * 访问数据库所用的用户名
     * 默认为：root
     */
    private static final String DB_USER_NAME;

    /**
     * 访问数据库所用的密码
     * 默认为：123456
     */
    private static final String DB_PASSWORD;

    /**
     * 数据库连接池的初始连接数
     * 默认为：5
     */
    private static final String MIN_CONNECTION;

    /**
     * 数据库连接池的连接数步长值
     * 默认为：2
     */
    private static final String STEP_CONNECTION;

    /**
     * 数据库连接池的最大连接数
     * 默认为：50
     */
    private static final String MAX_CONNECTION;

    static {
        Properties prop = new Properties();
        try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(PROP_NAME)) {
            prop.load(in);
        } catch (IOException e) {
            System.out.println("加载配置文件db.properties失败，获取输入流出现异常");
            e.printStackTrace();
        }

        JDBC_NAME = prop.getProperty("jdbcName", "com.mysql.jdbc.Driver");
        DB_URL =  prop.getProperty("dbUrl", "jdbc:mysql://localhost:3306/car_rental_system?useSSL=false&useUnicode=true&characterEncoding=utf8");
        DB_USER_NAME = prop.getProperty("dbUserName", "root");
        DB_PASSWORD = prop.getProperty("dbPassword", "123456");
        MIN_CONNECTION = prop.getProperty("minConnection", "5");
        STEP_CONNECTION = prop.getProperty("stepConnection", "2");
        MAX_CONNECTION = prop.getProperty("maxConnection", "50");
    }

    public static String getJdbcName() {
        return JDBC_NAME;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUserName() {
        return DB_USER_NAME;
    }

    public static String getMinConnection() {
        return MIN_CONNECTION;
    }

    public static String getStepConnection() {
        return STEP_CONNECTION;
    }

    public static String getMaxConnection() {
        return MAX_CONNECTION;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }
}
