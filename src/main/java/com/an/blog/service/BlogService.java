package com.an.blog.service;

import com.an.blog.bean.Blog;
import com.an.blog.bean.Tags;

import java.util.List;
import java.util.Map;

public interface BlogService {

    public Blog getBlogById(Integer id);

    public Blog getBlogByTokenAndId(String token, Integer id);

    public List<Blog> getBlogListByMap(Map<String, Object> map);

    public List<Blog> getBlogListByTokenAndMap(String token, Map<String, Object> map);

    public Integer getBlogTotalByMap(Map<String, Object> map);

    public Integer getBlogTotalByTokenAndMap(String token, Map<String, Object> map);

    public boolean saveBlogByTokenAndBlog(String token, Blog blog);

    public boolean deleteBlogByTokenAndId(String token, Integer id);

    public boolean saveBlogTagsList(List<Tags> tagsList, Integer blogId);

    public boolean addBlogViewNumberById(Integer id);

}
