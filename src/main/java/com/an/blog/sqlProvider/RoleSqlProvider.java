package com.an.blog.sqlProvider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class RoleSqlProvider {

    public String select(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("role");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("name") != null) WHERE("name = #{name}");
            }
        }.toString();
        return queryStr;
    }

}
