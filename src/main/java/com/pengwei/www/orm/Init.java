package com.pengwei.www.orm;

import com.pengwei.www.orm.bean.ColumnBean;
import com.pengwei.www.orm.bean.DatabaseBean;
import com.pengwei.www.orm.bean.TableBean;
import com.pengwei.www.orm.config.Configuration;
import com.pengwei.www.orm.util.NameUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用于初始化的类
 *
 * @author spice
 * @date 2020/12/06 21:00
 */
public class Init {

    static {
        // 获取一个数据库连接
        Connection connection = null;
        try {
            Class.forName(Configuration.getJdbcName());
            connection = DriverManager.getConnection(Configuration.getDbUrl(), Configuration.getDbUserName(), Configuration.getDbPassword());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("orm:初始化失败");
            e.printStackTrace();
        }

        // 获取连接失败，直接退出程序
        if (Objects.isNull(connection)) {
            System.out.println("程序初始化失败");
            System.exit(-1);
        }

        Map<String, TableBean> tables = new HashMap<>();
        Map<Class, TableBean> relationMap = new HashMap<>();

        try {
            // 获取数据库元数据
            DatabaseMetaData metaData = connection.getMetaData();

            // 获取数据库中所有的表
            ResultSet tableSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
            while (tableSet.next()) {
                String tableName = tableSet.getString("TABLE_NAME");
                // ti为存储一张表中所有信息的变量
                TableBean tableBean = new TableBean(tableName, new HashMap<>(), new ArrayList<>());
                tables.put(tableName, tableBean);

                // 获取一张表中的所有字段
                ResultSet columnSet = metaData.getColumns(null, "%", tableName, "%");
                while (columnSet.next()) {
                    // ci为存储一个字段中所有信息的变量
                    ColumnBean columnBean = new ColumnBean(columnSet.getString("COLUMN_NAME"),
                            columnSet.getString("TYPE_NAME"), 0);
                    // 将字段信息放入表信息中
                    tableBean.getColumns().put(columnSet.getString("COLUMN_NAME"), columnBean);
                }

                // 获取一张表中的所有主键
                ResultSet keySet = metaData.getPrimaryKeys(null, "%", tableName);
                while (keySet.next()) {
                    // keyBean为存储一个主键所有信息的变量(现在还未是主键)
                    ColumnBean keyBean = tableBean.getColumns().get(keySet.getObject("COLUMN_NAME"));
                    // 将keyBean表示字段的设置为主键(现在才成为主键)
                    keyBean.setKeyType(1);
                    tableBean.getJointPriKeys().add(keyBean);
                }

                // 取唯一主键，方便使用
                // 现在也只能处理唯一主键的情况，数据表中将只设唯一主键
                if (tableBean.getJointPriKeys().size() > 0) {
                    tableBean.setUniquePriKey(tableBean.getJointPriKeys().get(0));
                }
            }
        } catch (SQLException e) {
            System.out.println("程序初始化失败");
            e.printStackTrace();
            System.exit(-1);
        }

        // 将表和表对应的实体类关联起来
        for (TableBean tableBean : tables.values()) {
            // 获取一个po类的全类名，如：com.demo.www.User
            StringBuilder className = new StringBuilder(Configuration.getPackageName()).append(".")
                    .append(NameUtil.methodNameConvertor(tableBean.getTableName()));
            try {
                // 通过反射获取对应的字节码文件
                Class cls = Class.forName(className.toString());
                // 将两者关联起来
                relationMap.put(cls, tableBean);
            } catch (ClassNotFoundException e) {
                System.out.println("程序初始化失败");
                e.printStackTrace();
                System.exit(-1);
            }
        }

        DatabaseBean.getInstance().setTables(tables);
        DatabaseBean.getInstance().setRelationMap(relationMap);
    }
}
