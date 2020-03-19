package com.an.blog.common;

public class Result<T> {

    /**返回错误码*/
    private Integer code;
    /**返回信息*/
    private String message;
    /**返回数据*/
    private T data;

    public Result() {
        this.code = ResultStatus.SUCCESS.getCode();
        this.message = ResultStatus.SUCCESS.getMessage();
    }

    public Result(ResultStatus resultStatus) {
        this(resultStatus, null);
    }

    public Result(ResultStatus resultStatus, T data) {
        this.data = data;
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public void setResultStatus(ResultStatus resultStatus){
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
