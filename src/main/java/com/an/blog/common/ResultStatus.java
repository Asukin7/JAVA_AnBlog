package com.an.blog.common;

public enum ResultStatus {

    SUCCESS(0, "成功"),
    UNKNOWN_ERROR(1, "未知错误"),
    UNAUTHORIZED(2, "没有权限"),
    LOGIN_NOT(101, "未登录"),
    LOGIN_FAIL(102, "登录失败"),
    LOGIN_EXPIRE(103, "登录过期"),
    LOGOUT_FAIL(111, "注销失败"),
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
