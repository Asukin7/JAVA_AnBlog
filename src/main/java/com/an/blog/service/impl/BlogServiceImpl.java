package com.an.blog.service.impl;

import com.an.blog.bean.Blog;
import com.an.blog.bean.Tags;
import com.an.blog.dao.BlogDao;
import com.an.blog.dao.BlogTagsDao;
import com.an.blog.dao.TagsDao;
import com.an.blog.service.BlogService;
import com.an.blog.util.PageUtil;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private TagsDao tagsDao;
    @Autowired
    private BlogTagsDao blogTagsDao;

    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getById(id);
    }

    @Override
    public Blog getBlogByTokenAndId(String token, Integer id) {
        // 获取作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null)  return null;// userId为空 错误

        return blogDao.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Blog> getBlogListByMap(Map<String, Object> map) {
        // 设置分页查找
        if (map.get("page") == null) map.put("page", 1);// 默认第一页
        else map.put("page", Integer.parseInt(String.valueOf(map.get("page"))));// 转Integer
        if (map.get("size") == null) map.put("size", 10);// 默认一页10条
        else map.put("size", Integer.parseInt(String.valueOf(map.get("size"))));// 转Integer
        map.put("start", PageUtil.getStart((Integer) map.get("page"), (Integer) map.get("size")));// 计算开始条数

        if (map.get("tagsId") != null) return blogDao.getListByTagsIdMap(map);
        else return blogDao.select(map);
    }

    @Override
    public List<Blog> getBlogListByTokenAndMap(String token, Map<String, Object> map) {
        // 设置作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId != null) map.put("userId", userId);// userId非空 设置
        else return null;// userId为空 错误

        // 设置分页查找
        if (map.get("page") == null) map.put("page", 1);// 默认第一页
        else map.put("page", Integer.parseInt(String.valueOf(map.get("page"))));// 转Integer
        if (map.get("size") == null) map.put("size", 10);// 默认一页10条
        else map.put("size", Integer.parseInt(String.valueOf(map.get("size"))));// 转Integer
        map.put("start", PageUtil.getStart((Integer) map.get("page"), (Integer) map.get("size")));// 计算开始条数

        return blogDao.select(map);
    }

    @Override
    public Integer getBlogTotalByMap(Map<String, Object> map) {
        if (map.get("tagsId") != null) return blogDao.getTotalByTagsIdMap(map);
        else return blogDao.selectTotal(map);
    }

    @Override
    public Integer getBlogTotalByTokenAndMap(String token, Map<String, Object> map) {
        // 设置作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId != null) map.put("userId", userId);// userId非空 设置
        else return null;// userId为空 错误

        return blogDao.selectTotal(map);
    }

    @Override
    public boolean saveBlogByTokenAndBlog(String token, Blog blog) {
        // 设置作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId != null) blog.setUserId(userId);// userId非空 设置
        else return false;// userId为空 错误

        // 检查state
        if (blog.getState() != null) {
            Date now = new Date();
            switch (blog.getState()) {
                case 0: {// 0表示草稿箱
                    blog.setEditDate(now);// 设置编辑时间editDate
                    break;
                }
                case 1: {// 1表示已发表
                    blog.setPublishDate(now);// 设置发布时间publishDate
                    blog.setEditDate(now);// 设置编辑时间editDate
                    break;
                }
                case 2: {// 2表示已删除
                    blog.setEditDate(now);// 设置编辑时间editDate
                    break;
                }
                default: {// 其他 错误
                    return false;
                }
            }
        }

        Integer saveNumber;
        if (blog.getId() == null) {// blogId为空 插入
            // 检查categoryId
            if (blog.getCategoryId() == null) return false;// categoryId为空 错误
            // 检查state
            if (blog.getState() == null) return false;// state为空 错误
            saveNumber = blogDao.insert(blog);
        } else {// blogId非空 修改
            saveNumber = blogDao.update(blog);
        }
        if (saveNumber == null || saveNumber == 0) {
            return false;
        }

        if (!saveBlogTagsList(blog.getTagsList(), blog.getId())) return false;// 保存标签

        return true;
    }

    @Override
    public boolean deleteBlogByTokenAndId(String token, Integer id) {
        // 获取作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null)  return false;// userId为空 错误

        if (blogDao.getByIdAndUserId(id, userId) == null) return false;// 验证作者

        blogTagsDao.deleteByBlogId(id);// 删除此篇blog的所有tags

        Integer deleteNumber = blogDao.deleteById(id);// 删除此篇blog
        if (deleteNumber == null || deleteNumber == 0) return false;
        else return true;
    }

    @Override
    public boolean saveBlogTagsList(List<Tags> tagsList, Integer blogId) {
        blogTagsDao.deleteByBlogId(blogId);// 删除此篇blog的所有tags
        if (tagsList == null) return true;
        if (tagsList.size() == 0) return true;

        List<Integer> tagsIdList = new ArrayList<Integer>();
        for (Tags tags : tagsList) {
            Tags tagsQuery = tagsDao.getByName(tags.getName());// 查询是否存在tags
            if (tagsQuery != null) {
                tagsIdList.add(tagsQuery.getId());// 存在则获取id
            } else {
                tagsDao.insert(tags);// 不存在则插入tags
                tagsIdList.add(tags.getId());// 并获取id
            }
        }

        Integer insertNumber = blogTagsDao.insertByTagsIdListAndBlogId(tagsIdList, blogId);// 为此篇blog添加tags
        if (tagsList.size() == insertNumber) return true;
        else return false;
    }

    @Override
    public boolean addBlogViewNumberById(Integer id) {
        Integer addNumber = blogDao.addViewNumberById(id);
        if (addNumber == null || addNumber == 0) return false;
        else return true;
    }

}
