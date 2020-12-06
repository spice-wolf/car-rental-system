package com.pengwei.www.orm.bean;

/**
 * 表字段实体类
 * 存在表字段的部分信息
 *
 * @author spice
 * @date 2020/12/06 20:17
 */
public class ColumnBean {

    /**
     * 字段的名称
     */
    private String columnName;

    /**
     * 字段的数据类型
     */
    private String columnType;

    /**
     * 字段的键类型
     * 0代表普通键，1代表主键
     */
    private int keyType;

    public ColumnBean() {
    }

    /**
     * 创建一个ColumnBean对象
     *
     * @param columnName 字段的名称
     * @param columnType 字段的数据类型
     * @param keyType 字段的键类型
     */
    public ColumnBean(String columnName, String columnType, int keyType) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.keyType = keyType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
}
