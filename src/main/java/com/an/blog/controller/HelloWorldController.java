package com.an.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @ResponseBody
    @RequiresRoles("user")
    @GetMapping("/HelloWorld")
    public String HelloWorld() {
        return "Hello world!";
    }

}
