package com.an.blog.controller;

import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

/**
*权限异常处理，控制器都应该继承
*/
@RestControllerAdvice
@RequestMapping("/error")
public class ExceptionController {

    // 身份验证异常
    @RequestMapping("/authentication")
    public Result authentication() {
        return new Result(ResultStatus.AUTHENTICATION);
    }

    // 没有权限
    @ExceptionHandler(UnauthorizedException.class)
    @RequestMapping("/unauthorized")
    public Result unauthorized() {
        return new Result(ResultStatus.UNAUTHORIZED);
    }

}
