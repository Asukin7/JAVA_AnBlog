package com.an.blog.service.impl;

import com.an.blog.bean.BlogLike;
import com.an.blog.dao.BlogLikeDao;
import com.an.blog.service.BlogLikeService;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("blogLikeService")
public class BlogLikeServiceImpl implements BlogLikeService {

    @Autowired
    private BlogLikeDao blogLikeDao;

    @Override
    public boolean insertByTokenAndBlogLike(String token, BlogLike blogLike) {
        // 获取用户id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId != null) blogLike.setUserId(userId);
        else return false;// userId为空 错误

        // 获取当前日期
        blogLike.setDate(new Date());

        // 删除该用户对当前博客的赞
        blogLikeDao.deleteByUserIdAndBlogId(userId, blogLike.getBlogId());
        // 添加该用户对当前博客的赞
        Integer insertNumber = blogLikeDao.insert(blogLike);
        if (insertNumber == null || insertNumber == 0) return false;
        else return true;
    }

    @Override
    public boolean deleteByTokenAndBlogId(String token, Integer blogId) {
        // 获取用户id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null) return false;// userId为空 错误

        // 删除该用户对当前博客的赞
        Integer deleteNumber = blogLikeDao.deleteByUserIdAndBlogId(userId, blogId);
        if (deleteNumber == null || deleteNumber == 0) return false;
        else return true;
    }

}
