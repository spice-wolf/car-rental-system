package com.pengwei.www.datasource;

import com.pengwei.www.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 数据库连接池
 *
 * @author spice
 * @date 2020/12/06 17:47
 */
public class ConnectionPool {

    /**
     * 数据库连接池初始化时池中的连接数
     */
    private static String min = null;

    /**
     * 扩展数据库连接池时的步长值
     */
    private static String step = null;

    /**
     * 数据库连接池中最大的连接数
     */
    private static String max = null;

    /**
     * 记录连接池中的数据库连接的数量
     */
    private static int poolSize = 0;

    /**
     * 创建一个数据库连接池对象
     */
    public ConnectionPool() {
    }

    /**
     * 存放数据库连接的容器，即数据库连接池
     */
    private static final ConcurrentLinkedQueue<Connection> pool = new ConcurrentLinkedQueue<>();

    static {
        if (poolSize == 0 && pool.isEmpty()) {
            synchronized (ConnectionPool.class) {
                if (poolSize == 0 && pool.isEmpty()) {
                    min = Configuration.getMinConnection();
                    step = Configuration.getStepConnection();
                    max = Configuration.getMaxConnection();

                    for (int i = 0; i < Integer.parseInt(min); i++) {
                        pool.add(createConnection());
                        poolSize++;
                    }
                }
            }
        }
    }

    /**
     * 从数据库连接池中获取连接
     *
     * @return 一个数据库连接
     */
    public Connection getConnection() {
        synchronized (pool) {
            if (!pool.isEmpty()) {
                return pool.poll();
            } else {
                expand(Integer.parseInt(step));

                while (true) {
                    if (!pool.isEmpty()) {
                        return pool.poll();
                    }
                }
            }
        }
    }

    /**
     * 扩展数据库连接池
     *
     * @param step 每次扩展连接池时的步长值
     */
    private static void expand(int step) {
        if (poolSize > Integer.parseInt(max)) {
            System.out.println("服务繁忙，请稍后再试 X.X");
            return;
        }

        for (int i = 0; i < step; i++) {
            pool.offer(createConnection());
            poolSize++;
        }
    }

    /**
     * 回收数据库连接，将其重新放入数据库连接池
     *
     * @param conn 要回收的Connection对象
     */
    public void recycle(Connection conn) {
        if (conn != null) {
            pool.offer(conn);
        }
    }

    /**
     * 销毁数据库连接池
     */
    public void destroy() {
        for (Connection conn : pool) {
            try {
                conn.close();
                pool.remove(conn);
            } catch (SQLException e) {
                System.out.println("程序错误，数据库连接池销毁失败");
                e.printStackTrace();
            }
        }

        poolSize = 0;
    }

    /**
     * 创建一个数据库连接
     *
     * @return 数据库连接
     */
    private static Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName(Configuration.getJdbcName());
            conn = DriverManager.getConnection(Configuration.getDbUrl(), Configuration.getDbUserName(), Configuration.getDbPassword());
        } catch (ClassNotFoundException e) {
            System.out.println("获取数据库驱动出现异常，请检查数据库驱动是否正确");
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("获取数据库连接出现异常");
            throwables.printStackTrace();
        }

        return conn;
    }
}
