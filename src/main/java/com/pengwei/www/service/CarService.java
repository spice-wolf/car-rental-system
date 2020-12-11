package com.pengwei.www.service;

import com.pengwei.www.entity.po.Car;
import com.pengwei.www.result.CommonResult;

/**
 * 汽车服务接口
 *
 * @author spice
 * @date 2020/12/11 20:21
 */
public interface CarService {

    /**
     * 添加一辆汽车
     *
     * @param car 包含汽车信息的Car对象
     * @return 操作结果
     */
    CommonResult<Void> addCar(Car car);
}
