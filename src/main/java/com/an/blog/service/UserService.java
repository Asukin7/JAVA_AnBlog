package com.an.blog.service;

import com.an.blog.bean.User;

import java.util.List;

public interface UserService {

    public boolean register(User user);

    public String login(User user);

    public User getById(Integer id);

    public List<String> getRolesByUserId(Integer userId);

}