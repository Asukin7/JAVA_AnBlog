package com.an.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.an.blog.bean.User;
import com.an.blog.common.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {

    @RequiresRoles("user")
    @GetMapping("/HelloWorld")
    public Result HelloWorld() {
        return new Result();
    }

    @PostMapping("/test")
    public Result test(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject);
        User user = jsonObject.getJSONObject("user").toJavaObject(User.class);
        System.out.println(user);
        List<User> users = jsonObject.getJSONArray("users").toJavaList(User.class);
        System.out.println(users);
        return null;
    }

}
