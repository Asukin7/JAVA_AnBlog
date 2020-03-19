package com.an.blog.controller;

import com.an.blog.bean.User;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Result result = new Result();
        userService.register(user);
        return result;
    }

    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = new Result();
        String token = userService.login(user);
        if (token == null) result.setResultStatus(ResultStatus.LOGIN_FAIL);// 登录失败
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("token", token);
        result.setData(data);
        return result;
    }

}
