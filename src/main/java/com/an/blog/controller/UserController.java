package com.an.blog.controller;

import com.an.blog.bean.User;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresRoles("user")
    @GetMapping("/info")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        Result result = new Result();

        User user = userService.getUserInfoByToken(token);
        if (user == null) {
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("user", user);
        result.setData(resultData);
        return result;
    }

}
