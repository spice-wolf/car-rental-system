package com.pengwei.www.data;

import com.pengwei.www.entity.po.Role;
import com.pengwei.www.entity.po.User;

import java.util.List;

/**
 * 已登录用户的一些信息
 *
 * @author spice
 * @date 2020/12/07 11:16
 */
public class LoginDataProvider {

    /**
     * 实例
     */
    private static volatile LoginDataProvider INSTANCE = null;

    /**
     * 已登录用户
     */
    private User loginUser;

    /**
     * 已登录用户的角色
     */
    private List<Role> roleList;

    public static LoginDataProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginDataProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginDataProvider();
                }
            }
        }

        return INSTANCE;
    }

    public User getLoginUser() {
        return this.loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
