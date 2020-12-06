package com.pengwei.www.entity.po;

import java.time.LocalDateTime;

/**
 * 历史记录（出租和归还）实体类
 *
 * @author spice
 * @date 2020/12/07 2:47
 */
public class History {

    /**
     * 主键
     */
    private Long id;

    /**
     * 汽车id
     */
    private Long carId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 记录的时间
     */
    private LocalDateTime date;

    /**
     * 类型
     * 0表示是出租记录，1表示是归还记录
     */
    private Byte type;

    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
