package com.pengwei.www.dao;

import com.pengwei.www.datasource.DataSourceUtil;
import com.pengwei.www.entity.po.User;
import com.pengwei.www.orm.util.SqlUtil;
import com.pengwei.www.util.StringUtil;

import java.sql.Connection;

/**
 * 用户dao类
 *
 * @author spice
 * @date 2020/12/07 3:16
 */
public class UserDao {

    /**
     * 根据用户名或者用户名和密码来查询一个用户
     *
     * @param user 查询条件（用户名或者用户名和密码）
     * @return 查询结果
     */
    public User selectUser(User user) {
        String sqlOne = "select id, name, password from user where name = ?";
        String sqlTwo = " and password = ?";
        Connection connection = DataSourceUtil.getConnection();

        try {
            if (StringUtil.isEmpty(user.getPassword())) {
                return (User) SqlUtil.queryUniqueRow(connection, sqlOne, User.class, new Object[]{user.getName()});
            } else {
                return (User) SqlUtil.queryUniqueRow(connection, sqlOne + sqlTwo, User.class, new Object[]{user.getName(), user.getPassword()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }
}
