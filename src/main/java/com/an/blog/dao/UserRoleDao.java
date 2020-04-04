package com.an.blog.dao;

import com.an.blog.bean.Role;
import com.an.blog.bean.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserRoleDao {

    @Select("SELECT role.* FROM user_role LEFT JOIN role ON role.id = user_role.roleId WHERE user_role.userId = #{userId}")
    public List<Role> getRoleListByUserId(Integer userId);

    @Select("SELECT * FROM user_role WHERE roleId = #{roleId}")
    public List<UserRole> getByRoleId(Integer roleId);

    @Insert("INSERT INTO user_role(userId, roleId) VALUES(#{userId}, #{roleId})")
    public Integer insert(Integer userId, Integer roleId);

    @Delete("DELETE FROM user_role WHERE id = #{id}")
    public Integer deleteById(Integer id);

}
