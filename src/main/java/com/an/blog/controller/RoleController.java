package com.an.blog.controller;

import com.an.blog.bean.Role;
import com.an.blog.common.Result;
import com.an.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/tourist/roleList")
    public Result touristGetRoleList() {
        Result result = new Result();

        List<Role> roleList = roleService.getList();

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("roleList", roleList);
        result.setData(resultData);
        return result;
    }

}
