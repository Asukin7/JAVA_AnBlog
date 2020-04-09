package com.an.blog.dao;

import com.an.blog.bean.User;
import com.an.blog.sqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserDao {

    @SelectProvider(type = UserSqlProvider.class, method = "select")
    public List<User> select(Map<String, Object> map);

    @Select("SELECT * FROM user WHERE id = #{id}")
    public User getById(Integer id);

    @Select("SELECT id, enabled, nickname, introduction, profilePhoto, appreciationCode FROM user WHERE id = #{id}")
    public User getInfoById(Integer id);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    public User login(String username, String password);

    @InsertProvider(type = UserSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(User user);

    @UpdateProvider(type = UserSqlProvider.class, method = "update")
    public Integer update(User user);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateInfo")
    public Integer updateInfo(User user);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateSafe")
    public Integer updateSafe(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    public Integer deleteById(Integer id);

}
