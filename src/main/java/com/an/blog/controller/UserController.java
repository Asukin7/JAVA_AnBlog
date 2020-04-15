package com.an.blog.controller;

import com.an.blog.bean.User;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresRoles("user")
    @GetMapping("/user/info")
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

    @RequiresRoles("user")
    @GetMapping("/user/safe")
    public Result getUserSafe(@RequestHeader("Authorization") String token) {
        Result result = new Result();

        User user = userService.getUserSafeByToken(token);
        if (user == null) {
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("user", user);
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @PutMapping("/user/info")
    public Result updateUserInfo(@RequestHeader("Authorization") String token, @RequestBody User user) {
        Result result = new Result();

        if (!userService.updateUserInfoByTokenAndUser(token, user)) {
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @RequiresRoles("user")
    @PutMapping("/user/passwordChange")
    public Result passwordChange(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> map) {
        Result result = new Result();

        // 验证账号密码
        if (!userService.checkUserByTokenAndPassword(token, map.get("passwordOriginal").toString())) {
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        // 修改账号密码
        if (!userService.updatePasswordByTokenAndPassword(token, map.get("passwordChanged").toString())) {
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @PostMapping("/tourist/login")
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
        String token = userService.getTokenByUser(userLogin);
        if (token == null) {// token生成失败
            result.setResultStatus(ResultStatus.TOKEN_CREAT_FAIL);
            return result;
        }

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("token", token);
        result.setData(resultData);
        return result;
    }

    @PostMapping("/tourist/registerStep1")
    public Result registerStep1(@RequestBody User user) {
        Result result = new Result();

        if (userService.existUsername(user)) {// 账号已使用
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        if (userService.existEmail(user)) {// 邮箱已使用
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        if (userService.isEmailSend(user)) {// 已向邮箱发送邮件
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        if (!userService.registerStep1(user)) {// 用户注册失败
            result.setResultStatus(ResultStatus.USER_REGISTER_FAIL);
            return result;
        }

        return result;
    }

    @PostMapping("/tourist/registerStep2")
    public Result registerStep2(@RequestBody User user) {
        Result result = new Result();

        if (!userService.registerStep2(user)) {// 用户注册失败
            result.setResultStatus(ResultStatus.USER_REGISTER_FAIL);
            return result;
        }

        return result;
    }

    @PostMapping("/tourist/passwordResetStep1")
    public Result passwordResetStep1(@RequestBody User user) {
        Result result = new Result();

        if (!userService.existUsernameAndEmail(user)) {// 账号与邮箱不符
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        if (userService.isEmailSend(user)) {// 已向邮箱发送邮件
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        if (!userService.passwordResetStep1(user)) {// 重置密码失败
            result.setResultStatus(ResultStatus.USER_REGISTER_FAIL);
            return result;
        }

        return result;
    }

    @PostMapping("/tourist/passwordResetStep2")
    public Result passwordResetStep2(@RequestBody User user) {
        Result result = new Result();

        if (!userService.passwordResetStep2(user)) {// 重置密码失败
            result.setResultStatus(ResultStatus.USER_REGISTER_FAIL);
            return result;
        }

        return result;
    }

}
