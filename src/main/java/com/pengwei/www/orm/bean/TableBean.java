package com.pengwei.www.orm.bean;

import java.util.List;
import java.util.Map;

/**
 * 数据库表实体类
 * 存放数据库表的部分必要信息
 *
 * @author spice
 * @date 2020/12/06 20:16
 */
public class TableBean {

    /**
     * 数据表的名称
     */
    private String tableName;

    /**
     * 数据表中所有字段的信息
     */
    private Map<String, ColumnBean> columns;

    /**
     * 数据表中的唯一主键
     */
    private ColumnBean uniquePriKey;

    /**
     * 数据表中的联合主键
     */
    private List<ColumnBean> jointPriKeys;

    public TableBean() {
    }

    /**
     * 创建一个TTableBean对象
     *
     * @param tableName 数据表的名称
     * @param columns 数据表中所有字段的信息
     * @param uniquePriKey 数据表中的唯一主键
     */
    public TableBean(String tableName, Map<String, ColumnBean> columns, ColumnBean uniquePriKey) {
        this.tableName = tableName;
        this.columns = columns;
        this.uniquePriKey = uniquePriKey;
    }

    /**
     * 创建一个TableBean对象
     * @param tableName 数据表的名称
     * @param columns 数据表中所有字段的信息
     * @param jointPriKeys 数据表中的联合主键
     */
    public TableBean(String tableName, Map<String, ColumnBean> columns, List<ColumnBean> jointPriKeys) {
        this.tableName = tableName;
        this.columns = columns;
        this.jointPriKeys = jointPriKeys;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, ColumnBean> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnBean> columns) {
        this.columns = columns;
    }

    public ColumnBean getUniquePriKey() {
        return uniquePriKey;
    }

    public void setUniquePriKey(ColumnBean uniquePriKey) {
        this.uniquePriKey = uniquePriKey;
    }

    public List<ColumnBean> getJointPriKeys() {
        return jointPriKeys;
    }

    public void setJointPriKeys(List<ColumnBean> jointPriKeys) {
        this.jointPriKeys = jointPriKeys;
    }
}
