package com.an.blog.service.impl;

import com.an.blog.bean.Role;
import com.an.blog.bean.User;
import com.an.blog.dao.UserDao;
import com.an.blog.dao.UserRoleDao;
import com.an.blog.service.UserService;
import com.an.blog.util.MD5Util;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 用户注册
     * @param user
     * @return 成功返回true，失败返回false
     */
    @Override
    public boolean register(User user) {
        boolean result = false;
        // 用户密码使用MD5加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        // 插入用户数据
        if (userDao.insert(user) > 0) result = true;
        else result = false;
        // 插入用户角色数据
        if (userRoleDao.insert(user.getId(), 2) > 0) result = true;
        else result = false;
        return result;
    }

    /**
     * 用户登录
     * @param user
     * @return 成功返回用户信息，失败返回null
     */
    @Override
    public User login(User user) {
        // 用户密码使用MD5加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return userDao.login(user.getUsername(), user.getPassword());
    }

    /**
     * 生成token
     * @param user
     * @return 成功返回token，失败返回null
     */
    @Override
    public String getTokenByUser(User user) {
        return TokenUtil.creatToken(user.getId(), user.getUsername());
    }

    @Override
    public User getUserInfoById(Integer id) {
        return userDao.getInfoById(id);
    }

    @Override
    public User getUserInfoByToken(String token) {
        // 获取id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id == null)  return null;// id为空 错误

        return userDao.getInfoById(id);
    }

    @Override
    public List<Role> getRoleListByUserId(Integer userId) {
        return userRoleDao.getRoleListByUserId(userId);
    }


    @Override
    public boolean updateUserInfoByTokenAndUser(String token, User user) {
        // 设置id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id != null) user.setId(id);// id非空 设置
        else return false;// id为空 错误
        // 设置username
        String username = TokenUtil.getTokenData(token, "username").asString();// username
        if (username != null) user.setUsername(username);// username非空 设置
        else return false;// username为空 错误

        Integer updateNumber = userDao.updateInfo(user);
        if (updateNumber == null || updateNumber == 0) return false;
        else return true;
    }

}
