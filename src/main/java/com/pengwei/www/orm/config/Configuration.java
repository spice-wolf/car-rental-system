package com.pengwei.www.orm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * orm配置类
 *
 * @author spice
 * @date 2020/12/06 20:55
 */
public class Configuration {

    /**
     * orm配置文件的名称
     */
    private static final String PROP_NAME = "orm.properties";

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
     * 项目的包名
     */
    private static final String PACKAGE_NAME;

    static {
        Properties prop = new Properties();
        try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(PROP_NAME)) {
            prop.load(in);
        } catch (IOException e) {
            System.out.println("加载配置文件orm.properties失败，获取输入流出现异常");
            e.printStackTrace();
        }

        PACKAGE_NAME = prop.getProperty("orm.packageName", "com.demo.www.po");
        JDBC_NAME = prop.getProperty("orm.jdbcName", "com.mysql.jdbc.Driver");
        DB_URL =  prop.getProperty("orm.dbUrl", "jdbc:mysql://localhost:3306/car_rental_system?useSSL=false&useUnicode=true&characterEncoding=utf8");
        DB_USER_NAME = prop.getProperty("orm.dbUserName", "root");
        DB_PASSWORD = prop.getProperty("orm.dbPassword", "123456");
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

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    public static String getPackageName() {
        return PACKAGE_NAME;
    }
}
