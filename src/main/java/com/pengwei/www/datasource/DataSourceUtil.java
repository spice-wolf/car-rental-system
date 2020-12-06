package com.pengwei.www.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 数据库连接工具类
 *
 * @author spice
 * @date 2020/12/06 18:59
 */
public class DataSourceUtil {

    /**
     * 数据库连接池对象
     */
    private static ConnectionPool connectionPool = ConnectionPoolFactory.factoryProvider().createConnectionPool();

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public static Connection getConnection() {
        return connectionPool.getConnection();
    }

    /**
     * 关闭数据库连接
     *
     * @param rs ResultSet对象
     * @param preparedStatement PreparedStatement对象
     * @param connection Connection对象
     */
    public static void closeConnection(ResultSet rs, PreparedStatement preparedStatement, Connection connection) {
        if (Objects.nonNull(rs)) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("程序异常，关闭数据库连接失败");
                e.printStackTrace();
            }
        }

        if (Objects.nonNull(preparedStatement)) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("程序异常，关闭数据库连接失败");
                e.printStackTrace();
            }
        }

        if (Objects.nonNull(connection)) {
            connectionPool.recycle(connection);
        }
    }

    /**
     * 关闭数据库连接池
     */
    public static void closeConnectionPool() {
        connectionPool.destroy();
    }
}
