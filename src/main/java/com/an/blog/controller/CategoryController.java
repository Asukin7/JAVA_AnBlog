package com.an.blog.controller;

import com.an.blog.bean.Category;
import com.an.blog.common.Result;
import com.an.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/tourist/categoryList")
    public Result touristGetCategoryList() {
        Result result = new Result();

        List<Category> categoryList = categoryService.getList();

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("categoryList", categoryList);
        result.setData(resultData);
        return result;
    }

}
