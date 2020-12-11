package com.pengwei.www.service.impl;

import com.pengwei.www.dao.CarDao;
import com.pengwei.www.entity.po.Car;
import com.pengwei.www.result.CommonResult;
import com.pengwei.www.service.CarService;
import com.pengwei.www.util.StringUtil;

import java.util.Objects;

/**
 * @author spice
 * @date 2020/12/11 20:23
 */
public class CarServiceImpl implements CarService {

    private CarDao carDao = new CarDao();

    @Override
    public CommonResult<Void> addCar(Car car) {
        if (Objects.isNull(car) ||
            StringUtil.isEmpty(car.getName()) || StringUtil.isEmpty(car.getNumber()) ||
            Objects.isNull(car.getRentType()) || Objects.isNull(car.getRent())) {
            return CommonResult.operateFailWithMessage("添加失败，汽车信息不完整");
        }

        if (Objects.nonNull(carDao.selectCarByNumber(car.getNumber()))) {
            return CommonResult.operateFailWithMessage("添加失败，该车牌号已存在");
        }

        if (carDao.saveCar(car) == 1) {
            return CommonResult.operateSuccess();
        } else {
            return CommonResult.operateFailWithMessage("程序出错，添加汽车失败");
        }
    }
}
