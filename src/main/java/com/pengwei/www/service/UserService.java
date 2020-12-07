package com.pengwei.www.service;

import com.pengwei.www.entity.po.User;
import com.pengwei.www.result.CommonResult;

/**
 * 用户接口
 *
 * @author spice
 * @date 2020/12/07 3:10
 */
public interface UserService {

    /**
     * 用户登录接口
     *
     * @param user 要登录的用户
     * @return 操作结果
     */
    CommonResult<Void> login(User user);

    /**
     * 用户注册接口
     *
     * @param user 要注册的用户
     * @return 操作结果
     */
    CommonResult<Void> register(User user);
}
