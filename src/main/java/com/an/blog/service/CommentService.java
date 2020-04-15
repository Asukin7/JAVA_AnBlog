package com.an.blog.service;

import com.an.blog.bean.Comment;

import java.util.List;

public interface CommentService {

    public Comment getCommentById(Integer id);

    public List<Comment> getRootCommentListByBlogIdAndPage(Integer blogId, Integer page);

    public List<Comment> getChildCommentListByRootIdAndPage(Integer rootId, Integer page);

    public Integer getRootCommentTotalByBlogId(Integer blogId);

    public Integer getChildCommentTotalByRootId(Integer rootId);

    public List<Comment> getRootCommentListByTokenAndBlogIdAndPage(String token, Integer blogId, Integer page);

    public List<Comment> getChildCommentListByTokenAndRootIdAndPage(String token, Integer rootId, Integer page);

    public boolean insertByTokenAndComment(String token, Comment comment);

    public boolean deleteByTokenAndId(String token, Integer id);

}
