package com.an.blog.dao;

import com.an.blog.bean.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {

    @Select("SELECT * FROM role")
    public List<Role> getList();

    @Select("SELECT * FROM role WHERE id = #{id}")
    public Role getById(Integer id);

    @Insert("INSERT INTO role(name) VAlUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(Role role);

    @Update("UPDATE role SET name = #{name} WHERE id = #{id}")
    public Integer update(Role role);

    @Delete("DELETE FROM role WHERE id = #{id}")
    public Integer deleteById(Integer id);

}
