package com.an.blog.service.impl;

import com.an.blog.bean.Comment;
import com.an.blog.dao.CommentDao;
import com.an.blog.service.CommentService;
import com.an.blog.util.PageUtil;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment getCommentById(Integer id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getRootCommentListByBlogIdAndPage(Integer blogId, Integer page) {
        return commentDao.getRootCommentListByBlogIdAndStart(blogId, PageUtil.getStart(page, 10));
    }

    @Override
    public List<Comment> getChildCommentListByRootIdAndPage(Integer rootId, Integer page) {
        return commentDao.getChildCommentListByRootIdAndStart(rootId, PageUtil.getStart(page, 10));
    }

    @Override
    public Integer getRootCommentTotalByBlogId(Integer blogId) {
        return commentDao.getRootTotalByBlogId(blogId);
    }

    @Override
    public Integer getChildCommentTotalByRootId(Integer rootId) {
        return commentDao.getChildTotalByRootId(rootId);
    }

    @Override
    public boolean insertByTokenAndComment(String token, Comment comment) {
        // 设置作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId != null) comment.setUserId(userId);// userId非空 设置
        else return false;// userId为空 错误

        // 设置时间
        comment.setPublishDate(new Date());

        Integer insertNumber = commentDao.insert(comment);
        if (insertNumber == null || insertNumber == 0) return false;
        else return true;
    }

}
