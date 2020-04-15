package com.an.blog.service;

import com.an.blog.bean.Role;
import com.an.blog.bean.User;

import java.util.List;

public interface UserService {

    public User login(User user);

    public boolean registerStep1(User user);

    public boolean registerStep2(User user);

    public boolean passwordResetStep1(User user);

    public boolean passwordResetStep2(User user);

    public boolean existUsername(User user);

    public boolean existEmail(User user);

    public boolean existUsernameAndEmail(User user);

    public boolean isEmailSend(User user);

    public String getTokenByUser(User user);

    public User getUserInfoById(Integer id);

    public List<Role> getRoleListByUserId(Integer userId);

    public User getUserInfoByToken(String token);

    public User getUserSafeByToken(String token);

    public boolean checkUserByTokenAndPassword(String token, String password);

    public boolean updateUserInfoByTokenAndUser(String token, User user);

    public boolean updatePasswordByTokenAndPassword(String token, String password);

}
