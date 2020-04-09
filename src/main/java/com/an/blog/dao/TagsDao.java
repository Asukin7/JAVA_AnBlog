package com.an.blog.dao;

import com.an.blog.bean.Tags;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TagsDao {

    @Select("SELECT * FROM tags ORDER BY RAND() LIMIT 10")
    public List<Tags> getListByRand();

    @Select("SELECT * FROM tags WHERE id = #{id}")
    public Tags getById(Integer id);

    @Select("SELECT * FROM tags WHERE name = #{name}")
    public Tags getByName(String name);

    @Insert("INSERT INTO tags(name) VAlUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(Tags tags);

    @Update("UPDATE tags SET name = #{name} WHERE id = #{id}")
    public Integer update(Tags tags);

    @Delete("DELETE FROM tags WHERE id = #{id}")
    public Integer deleteById(Integer id);

}
