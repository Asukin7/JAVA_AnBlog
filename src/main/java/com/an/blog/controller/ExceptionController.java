package com.an.blog.controller;

import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
*权限异常处理，控制器都应该继承
*/
@ControllerAdvice
@RequestMapping("/error")
public class ExceptionController {

    //拒绝请求
    @ResponseBody
    @RequestMapping("/unknown")
    public Result noToken(Throwable ex) {
        System.out.println(ex.getMessage());
        return new Result(ResultStatus.UNKNOWN_ERROR);
    }

    //无权时的异常处理
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    @RequestMapping("/unauthorized")
    public Result unauthorized(){
        return new Result(ResultStatus.UNAUTHORIZED);
    }

}
