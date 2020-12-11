package com.pengwei.www.result;

/**
 * 通用返回结果类
 *
 * @author spice
 * @date 2020/12/07 3:06
 */
public class CommonResult<T> {

    private CodeEnum code;

    private String message;

    private boolean isSuccess;

    private T data;

    public CommonResult(CodeEnum code, String message, boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public CommonResult(CodeEnum code, String message, boolean isSuccess, T data) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public static <E> CommonResult<E> operateSuccess(E e) {
        return new CommonResult<>(CodeEnum.SUCCESS, "操作成功", true, e);
    }

    public static <E> CommonResult<E> operateFailWithMessage(String message) {
        return new CommonResult<>(CodeEnum.FAIL, message, false);
    }

    public static CommonResult operateSuccess() {
        return new CommonResult<>(CodeEnum.SUCCESS, "操作成功", true, null);
    }

    public CodeEnum getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public T getData() {
        return data;
    }
}
