package com.pengwei.www.orm.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 数据库实体类
 * 存储数据库的所有表信息和表对应的实体类信息
 *
 * @author spice
 * @date 2020/12/06 20:41
 */
public class DatabaseBean {

    /**
     * Database实例对象
     */
    private static volatile DatabaseBean INSTANCE = null;

    /**
     * 数据库中所有数据表的集合
     * 其中key为表名，value为表实体类
     */
    private Map<String, TableBean> tables = new HashMap<>();

    /**
     * 数据库表和表对应的实体类的对应关系集合
     * key为表对应的实体类的Class对象，value为表实体类
     */
    private Map<Class, TableBean> relationMap = new HashMap<>();

    /**
     * 私有化构造方法
     */
    private DatabaseBean() {

    }

    /**
     * 获取Database实例对象
     *
     * @return Database实例对象
     */
    public static DatabaseBean getInstance() {
        // 双重检查锁保证单例
        if (Objects.isNull(INSTANCE)) {
            synchronized (DatabaseBean.class) {
                if (Objects.isNull(INSTANCE)) {
                    INSTANCE = new DatabaseBean();
                }
            }
        }

        return INSTANCE;
    }

    public Map<String, TableBean> getTables() {
        return tables;
    }

    public void setTables(Map<String, TableBean> tables) {
        this.tables = tables;
    }

    public Map<Class, TableBean> getRelationMap() {
        return relationMap;
    }

    public void setRelationMap(Map<Class, TableBean> relationMap) {
        this.relationMap = relationMap;
    }
}
