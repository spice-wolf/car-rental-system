package com.pengwei.www.dao;

import com.pengwei.www.datasource.DataSourceUtil;
import com.pengwei.www.entity.po.Car;
import com.pengwei.www.orm.util.SqlUtil;

import java.sql.Connection;

/**
 * 汽车dao类
 *
 * @author spice
 * @date 2020/12/11 20:24
 */
public class CarDao {

    /**
     * 根据车牌号查找汽车
     *
     * @param number 车牌号
     * @return 符合的汽车
     */
    public Car selectCarByNumber(String number) {
        String sql = "select id from car where number = ?";
        Connection connection = DataSourceUtil.getConnection();

        try {
            return (Car) SqlUtil.queryUniqueRow(connection, sql, Car.class, new Object[]{number});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }

    /**
     * 保存一个汽车对象
     *
     * @param car 汽车对象
     * @return 保存成功的数据条数
     */
    public int saveCar(Car car) {
        Connection connection = DataSourceUtil.getConnection();
        try {
            return SqlUtil.insert(connection, car);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }
}
