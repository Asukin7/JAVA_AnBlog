package com.an.blog.service.impl;

import com.an.blog.bean.User;
import com.an.blog.bean.UserRole;
import com.an.blog.dao.UserDao;
import com.an.blog.dao.UserRoleDao;
import com.an.blog.service.UserService;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public boolean register(User user) {
        boolean result = false;
        // 插入用户数据
        if (userDao.insert(user) > 0) result = true;
        else result = false;
        // 插入用户角色数据
        if (userRoleDao.insert(user.getId(), 2) > 0) result = true;
        else result = false;
        return result;
    }

    @Override
    public String login(User user) {
        String result = null;
        User userQuery = userDao.login(user.getUsername(), user.getPassword());
        if (userQuery != null) {// 登录成功
            if (userQuery.getEnabled() == 1) {// 账号有效
                result = TokenUtil.creatToken(userQuery.getId(), userQuery.getUsername());// 生成token
            }
        }
        return result;
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public List<String> getRolesByUserId(Integer userId) {
        List<UserRole> userRoleList = userRoleDao.getByUserId(userId);
        List<String> result = new ArrayList<String>();
        for (UserRole userRole : userRoleList) {
            result.add(userRole.getRole().getName());
        }
        return result;
    }

}
