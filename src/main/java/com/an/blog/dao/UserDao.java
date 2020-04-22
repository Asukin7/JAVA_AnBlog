package com.an.blog.dao;

import com.an.blog.bean.User;
import com.an.blog.sqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserDao {

    @SelectProvider(type = UserSqlProvider.class, method = "getListByMap")
    @Results(id = "", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleList", column = "id", many = @Many(select = "com.an.blog.dao.UserRoleDao.getRoleListByUserId"))
    })
    public List<User> getListByMap(Map<String, Object> map);

    @SelectProvider(type = UserSqlProvider.class, method = "getTotalByMap")
    public Integer getTotalByMap(Map<String, Object> map);

//    @Select("SELECT * FROM user WHERE id = #{id}")
//    public User getById(Integer id);

    @Select("SELECT id, enabled, nickname, introduction, profilePhoto, appreciationCode FROM user WHERE id = #{id}")
    public User getInfoById(Integer id);

    @Select("SELECT id, enabled, username, email FROM user WHERE id = #{id}")
    public User getSafeById(Integer id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    public User getByUsername(String username);

    @Select("SELECT * FROM user WHERE email = #{email}")
    public User getByEmail(String email);

    @Select("SELECT * FROM user WHERE username = #{username} AND email = #{email}")
    public User getByUsernameAndEmail(String username, String email);

    @Select("SELECT * FROM user WHERE id = #{id} AND username = #{username} AND password = #{password}")
    public User getByIdAndUsernameAndPassword(User user);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    public User login(String username, String password);

    @InsertProvider(type = UserSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(User user);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateInfo")
    public Integer updateInfo(User user);

    @Update("UPDATE user SET enabled = #{enabled} WHERE id = #{id}")
    public Integer updateEnabledById(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id} AND username = #{username}")
    public Integer updatePasswordByIdAndUsername(User user);

    @Update("UPDATE user SET password = #{password} WHERE username = #{username} AND email = #{email}")
    public Integer updatePasswordByUsernameAndEmail(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    public Integer deleteById(Integer id);

}
