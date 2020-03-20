package com.an.blog.common;

public enum ResultStatus {

    // 基础错误信息：X
    SUCCESS(0, "成功"),
    UNKNOWN(1, "未知错误"),
    AUTHENTICATION(2, "身份验证异常"),
    UNAUTHORIZED(3, "没有权限"),
    TOKEN_CREAT_FAIL(4, "token生成失败"),
    // 登录错误信息：10X
    LOGIN_NOT(101, "未登录"),
    LOGIN_EXPIRE(102, "登录过期"),
    LOGIN_ERROR(103, "账号密码错误"),
    LOGIN_DISABLE(104, "账号被封禁"),
    // 注销错误信息：11X
    LOGOUT_FAIL(111, "注销失败"),
    // 用户错误信息：2XX
    USER_REGISTER_FAIL(201, "用户注册失败"),
    USER_UPDATE_FAIL(202, "用户更新失败"),
    USER_DELETE_FAIL(203, "用户销毁失败");

    private final Integer code;
    private final String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultStatus{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
