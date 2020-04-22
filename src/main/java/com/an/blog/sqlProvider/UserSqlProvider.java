package com.an.blog.sqlProvider;

import com.an.blog.bean.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserSqlProvider {

    public String getListByMap(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("id, enabled, username, email, nickname, introduction, profilePhoto, appreciationCode");
                FROM("user");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("enabled") != null) WHERE("enabled = #{enabled}");
                if (map.get("username") != null) WHERE("username = #{username}");
                if (map.get("email") != null) WHERE("email = #{email}");
//                if (map.get("password") != null) WHERE("password = #{password}");
                if (map.get("nickname") != null) WHERE("nickname = #{nickname}");
                if (map.get("introduction") != null) WHERE("introduction = #{introduction}");
                if (map.get("profilePhoto") != null) WHERE("profilePhoto = #{profilePhoto}");
                if (map.get("appreciationCode") != null) WHERE("appreciationCode = #{appreciationCode}");
                LIMIT("#{start}, #{size}");
            }
        }.toString();
        return queryStr;
    }

    public String getTotalByMap(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("count(*)");
                FROM("user");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("enabled") != null) WHERE("enabled = #{enabled}");
                if (map.get("username") != null) WHERE("username = #{username}");
                if (map.get("email") != null) WHERE("email = #{email}");
//                if (map.get("password") != null) WHERE("password = #{password}");
                if (map.get("nickname") != null) WHERE("nickname = #{nickname}");
                if (map.get("introduction") != null) WHERE("introduction = #{introduction}");
                if (map.get("profilePhoto") != null) WHERE("profilePhoto = #{profilePhoto}");
                if (map.get("appreciationCode") != null) WHERE("appreciationCode = #{appreciationCode}");
            }
        }.toString();
        return queryStr;
    }

    public String insert(User user) {
        String queryStr = new SQL() {
            {
                INSERT_INTO("user");
                if (user.getEnabled() != null) VALUES("enabled", "#{enabled}");
                if (user.getUsername() != null) VALUES("username", "#{username}");
                if (user.getEmail() != null) VALUES("email", "#{email}");
                if (user.getPassword() != null) VALUES("password", "#{password}");
                if (user.getNickname() != null) VALUES("nickname", "#{nickname}");
                if (user.getIntroduction() != null) VALUES("introduction", "#{introduction}");
                if (user.getProfilePhoto() != null) VALUES("profilePhoto", "#{profilePhoto}");
                if (user.getAppreciationCode() != null) VALUES("appreciationCode", "#{appreciationCode}");
            }
        }.toString();
        return queryStr;
    }

    public String updateInfo(User user) {
        String queryStr = new SQL() {
            {
                UPDATE("user");
                if (user.getNickname() != null) SET("nickname = #{nickname}");
                if (user.getIntroduction() != null) SET("introduction = #{introduction}");
                if (user.getProfilePhoto() != null) SET("profilePhoto = #{profilePhoto}");
                if (user.getAppreciationCode() != null) SET("appreciationCode = #{appreciationCode}");
                WHERE("id = #{id}");
                WHERE("username = #{username}");
            }
        }.toString();
        return queryStr;
    }

}
