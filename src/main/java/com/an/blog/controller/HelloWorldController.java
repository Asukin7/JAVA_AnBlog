package com.an.blog.controller;

import com.an.blog.common.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequiresRoles("user")
    @GetMapping("/HelloWorld")
    public Result HelloWorld() {
        return new Result();
    }

}
