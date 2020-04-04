package com.an.blog.service.impl;

import com.an.blog.bean.Category;
import com.an.blog.dao.CategoryDao;
import com.an.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getList() {
        return categoryDao.getList();
    }
}
