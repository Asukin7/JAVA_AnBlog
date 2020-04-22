package com.an.blog.service.impl;

import com.an.blog.bean.Role;
import com.an.blog.dao.RoleDao;
import com.an.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getList() {
        return roleDao.getList();
    }

}
