package com.pengwei.www.entity.po;

import java.time.LocalDateTime;

/**
 * 汽车实体类
 *
 * @author spice
 * @date 2020/12/07 2:43
 */
public class Car {

    /**
     * 主键
     */
    private Long id;

    /**
     * 汽车的名称，必需
     */
    private String name;

    /**
     * 厂商
     */
    private String factory;

    /**
     * 车牌号，必需
     */
    private String number;

    /**
     * 出厂日期
     */
    private LocalDateTime productionDate;

    /**
     * 租借类型
     * 0表示小时，1表示天
     * 必需
     */
    private Byte rentType;

    /**
     * 租金，单位：元
     * 必需
     */
    private Double rent;

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public Byte getRentType() {
        return rentType;
    }

    public void setRentType(Byte rentType) {
        this.rentType = rentType;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }
}
