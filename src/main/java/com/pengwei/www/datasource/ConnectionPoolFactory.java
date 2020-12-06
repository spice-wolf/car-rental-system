package com.pengwei.www.datasource;

/**
 * 数据库连接池工厂类
 * 使用静态内部类实现
 *
 * @author spice
 * @date 2020/12/06 18:54
 */
public class ConnectionPoolFactory {

    private static class ConnectionPoolFactoryInstance {
        private static final ConnectionPoolFactory INSTANCE = new ConnectionPoolFactory();
    }

    private ConnectionPoolFactory() {
    }

    public static ConnectionPoolFactory factoryProvider() {
        return ConnectionPoolFactoryInstance.INSTANCE;
    }

    public ConnectionPool createConnectionPool() {
        return new ConnectionPool();
    }
}
