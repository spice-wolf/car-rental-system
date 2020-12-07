package com.pengwei.www.service.impl;

import com.pengwei.www.dao.UserDao;
import com.pengwei.www.dao.UserRoleDao;
import com.pengwei.www.data.LoginDataProvider;
import com.pengwei.www.entity.po.Role;
import com.pengwei.www.entity.po.User;
import com.pengwei.www.entity.po.UserRole;
import com.pengwei.www.result.CodeEnum;
import com.pengwei.www.result.CommonResult;
import com.pengwei.www.service.UserService;
import com.pengwei.www.util.CollectionUtil;
import com.pengwei.www.util.StringUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author spice
 * @date 2020/12/07 3:12
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDao();

    private UserRoleDao userRoleDao = new UserRoleDao();

    @Override
    public CommonResult<Void> login(User user) {
        if (Objects.nonNull(user)) {
            return new CommonResult<>(CodeEnum.ACCOUNT_NOT_EXIST, "用户不存在", false);
        }

        User userWithNameOnly = new User();
        userWithNameOnly.setName(user.getName());
        // 用户名不正确的情况
        if (StringUtil.isEmpty(user.getName()) || Objects.isNull(userDao.selectUser(userWithNameOnly))) {
            return new CommonResult<>(CodeEnum.ACCOUNT_NOT_EXIST, "用户不存在", false);
        }

        User loginUser = userDao.selectUser(user);
        // 密码错误的情况
        if (StringUtil.isEmpty(user.getPassword()) || Objects.isNull(loginUser)) {
            return new CommonResult<>(CodeEnum.PASSWORD_MISMATCH, "密码错误", false);
        }

        List<UserRole> userRoleList = userRoleDao.selectByUserId(loginUser.getId());
        if (CollectionUtil.isEmpty(userRoleList)) {
            return CommonResult.operateFailWithMessage("登录失败，程序异常");
        }

        List<Role> roleList = userRoleList.stream().map(UserRole::getRoleId).map(Role::new).collect(Collectors.toList());
        // 保存以登录用户的一些必要信息
        LoginDataProvider.getInstance().setLoginUser(loginUser);
        LoginDataProvider.getInstance().setRoleList(roleList);

        return CommonResult.operateSuccess(null);
    }

    @Override
    public CommonResult<Void> register(User user) {
        return null;
    }
}
