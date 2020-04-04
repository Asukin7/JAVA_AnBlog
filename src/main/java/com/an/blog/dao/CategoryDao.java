package com.an.blog.dao;

import com.an.blog.bean.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {

    @Select("SELECT * FROM category")
    public List<Category> getList();

}
