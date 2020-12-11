package com.pengwei.www.service;

import com.pengwei.www.entity.po.User;
import com.pengwei.www.result.CodeEnum;
import com.pengwei.www.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * 用户接口测试类
 *
 * @author spice
 * @date 2020/12/07 12:10
 */
public class UserServiceTest {

    private UserService userService = new UserServiceImpl();

    @Test
    public void loginTest() {
        User nullUser = null;
        Assert.assertEquals(CodeEnum.ACCOUNT_NOT_EXIST, userService.login(nullUser).getCode());

        // 一个用户名不存在的用户
        User userWithNameError = new User();
        userWithNameError.setName("$%&%*%");
        Assert.assertEquals(CodeEnum.ACCOUNT_NOT_EXIST, userService.login(userWithNameError).getCode());

        // 一个用户名正确、密码错误的用户
        User userWithPasswordError = new User();
        userWithPasswordError.setName("ming");
        userWithPasswordError.setPassword("$#%&$");
        Assert.assertEquals(CodeEnum.PASSWORD_MISMATCH, userService.login(userWithPasswordError).getCode());

        User rightUser = new User();
        rightUser.setName("ming");
        rightUser.setPassword("123456");
        Assert.assertEquals(CodeEnum.SUCCESS, userService.login(rightUser).getCode());
    }

    @Test
    public void registerTest() {
        User nullUser = null;
        Assert.assertEquals(CodeEnum.FAIL, userService.register(nullUser).getCode());

        User userWithNoName = new User();
        userWithNoName.setName("");
        Assert.assertEquals(CodeEnum.FAIL, userService.register(userWithNoName).getCode());

        User userWithNoPassword = new User();
        userWithNoPassword.setName("spice");
        userWithNoPassword.setPassword("");
        Assert.assertEquals(CodeEnum.FAIL, userService.register(userWithNoPassword).getCode());

        // 【用户名已被使用】的情况暂不测试
        // 【注册成功】的情况暂不测试
    }
}
