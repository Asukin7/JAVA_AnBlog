package com.an.blog.dao;

import com.an.blog.bean.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {

    @Select("SELECT * FROM category WHERE id = #{id}")
    public Category getById(Integer id);

    @Select("SELECT * FROM category")
    public List<Category> getList();

}
