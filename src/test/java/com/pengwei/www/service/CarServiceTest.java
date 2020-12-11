package com.pengwei.www.service;

import com.pengwei.www.entity.po.Car;
import com.pengwei.www.result.CodeEnum;
import com.pengwei.www.service.impl.CarServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * 汽车接口测试类
 *
 * @author spice
 * @date 2020/12/11 20:43
 */
public class CarServiceTest {

    private CarService carService = new CarServiceImpl();

    @Test
    public void addCarTest() {
        Car incompleteCar = new Car();
        incompleteCar.setName("某品牌汽车");
        Assert.assertEquals(CodeEnum.FAIL, carService.addCar(incompleteCar).getCode());

        Car carWithRepeatedNumber = new Car();
        carWithRepeatedNumber.setName("sss");
        carWithRepeatedNumber.setFactory("ppp");
        carWithRepeatedNumber.setNumber("numberForTest");
        carWithRepeatedNumber.setProductionDate(LocalDateTime.now());
        carWithRepeatedNumber.setRentType((byte) 0);
        carWithRepeatedNumber.setRent(120d);
        Assert.assertEquals(CodeEnum.FAIL, carService.addCar(carWithRepeatedNumber).getCode());

        // 【添加成功】暂不测试
    }
}
