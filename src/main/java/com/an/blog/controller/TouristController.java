package com.an.blog.controller;

import com.an.blog.bean.User;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Result result = new Result();

        if(!userService.register(user)) {// 用户注册失败
            result.setResultStatus(ResultStatus.USER_REGISTER_FAIL);
            return result;
        }

        return result;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = new Result();

        User userLogin = userService.login(user);
        if (userLogin == null) {// 账号密码错误
            result.setResultStatus(ResultStatus.LOGIN_ERROR);
            return result;
        }
        if (userLogin.getEnabled() == 0) {// 账号给封禁
            result.setResultStatus(ResultStatus.LOGIN_DISABLE);
            return result;
        }
        String token = userService.getTokenByUser(user);
        if (token == null) {// token生成失败
            result.setResultStatus(ResultStatus.TOKEN_CREAT_FAIL);
            return result;
        }

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("token", token);
        result.setData(resultData);
        return result;
    }

}
