package com.pengwei.www.dao;

import com.pengwei.www.datasource.DataSourceUtil;
import com.pengwei.www.entity.po.UserRole;
import com.pengwei.www.orm.util.SqlUtil;

import java.sql.Connection;
import java.util.List;

/**
 * 用户-角色对应关系dao类
 *
 * @author spice
 * @date 2020/12/07 11:20
 */
public class UserRoleDao {

    /**
     * 根据用户id查询对应的用户角色列表
     *
     * @param id 用户id
     * @return 查询结果
     */
    public List<UserRole> selectByUserId(Long id) {
        String sql = "select role_id from user_role where user_id = ?";
        Connection connection = DataSourceUtil.getConnection();

        try {
            return SqlUtil.queryRows(connection, sql, new UserRole(), new Object[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DataSourceUtil.closeConnection(connection);
        }
    }
}
