package com.pengwei.www.result;

/**
 * 返回码枚举类
 *
 * @author spice
 * @date 2020/12/07 3:03
 */
public enum CodeEnum {

    /**
     * 一些返回码
     */
    SUCCESS("操作成功", 200),
    FAIL("操作失败", 400),
    ACCOUNT_NOT_EXIST("账号不存在", 600),
    PASSWORD_MISMATCH("密码错误", 700);

    /**
     * 结果描述
     */
    private String description;

    /**
     * 结果值
     */
    private int value;

    CodeEnum(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public int getValue() {
        return this.value;
    }
}
