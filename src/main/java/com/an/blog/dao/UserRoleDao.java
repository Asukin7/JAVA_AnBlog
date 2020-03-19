package com.an.blog.dao;

import com.an.blog.bean.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserRoleDao {

    @Select("SELECT * FROM user_role WHERE userId = #{userId}")
    @Results(id = "UserRoleResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleId", column = "roleId"),
            @Result(property = "userId", column = "userId"),
            @Result(property = "role", column = "roleId", one = @One(select = "com.an.blog.dao.RoleDao.getById")),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getById"))
    })
    public List<UserRole> getByUserId(Integer userId);

    @Select("SELECT * FROM user_role WHERE roleId = #{roleId}")
    @ResultMap("UserRoleResult")
    public List<UserRole> getByRoleId(Integer roleId);

    @Insert("INSERT INTO user_role(userId, roleId) VALUES(#{userId}, #{roleId})")
    public Integer insert(Integer userId, Integer roleId);

    @Delete("DELETE FROM user_role WHERE id = #{id}")
    public Integer deleteById(Integer id);

}
