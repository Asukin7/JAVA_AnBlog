package com.an.blog.service.impl;

import com.an.blog.bean.Comment;
import com.an.blog.dao.BlogDao;
import com.an.blog.dao.CommentDao;
import com.an.blog.dao.CommentLikeDao;
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
    @Autowired
    private CommentLikeDao commentLikeDao;
    @Autowired
    private BlogDao blogDao;

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
    public List<Comment> getRootCommentListByTokenAndBlogIdAndPage(String token, Integer blogId, Integer page) {
        // 获取用户id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null) return null;// userId为空 错误

        return commentDao.getRootCommentListByUserIdAndBlogIdAndStart(userId, blogId, PageUtil.getStart(page, 10));
    }

    @Override
    public List<Comment> getChildCommentListByTokenAndRootIdAndPage(String token, Integer rootId, Integer page) {
        // 获取用户id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null) return null;// userId为空 错误

        return commentDao.getChildCommentListByUserIdAndRootIdAndStart(userId, rootId, PageUtil.getStart(page, 10));
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

    @Override
    public boolean deleteByTokenAndId(String token, Integer id) {
        // 获取作者id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null) return false;// userId为空 错误

        // 判断是否有删除权限
        Comment comment = commentDao.getById(id);// 获取评论
        if (userId != comment.getUserId()) {// 不是评论作者
            if (userId != blogDao.getById(comment.getBlogId()).getUserId()) {// 不是博主
                if (comment.getRootId() != null && comment.getRootId() != 0) {// 是子评论
                    if (userId != commentDao.getById(comment.getRootId()).getUserId()) {// 不是父评论作者
                        return false;// 不是博主 是子评论且不是评论作者且不是父评论作者 无权
                    }
                } else {// 是父评论
                    return false;// 不是博主 是父评论且不是评论作者 无权
                }
            }
        }

        // 删除评论
        if (comment.getRootId() != null && comment.getRootId() != 0) {// 是子评论
            // 删除评论的所有赞
            commentLikeDao.deleteByCommentId(id);
            // 删除评论
            Integer deleteNumber = commentDao.deleteById(id);
            if (deleteNumber == null || deleteNumber == 0) return false;
            else return true;
        } else {// 是父评论
            // 删除所有子评论的所有赞
            commentLikeDao.deleteByRootId(id);
            // 删除评论的所有赞
            commentLikeDao.deleteByCommentId(id);
            // 删除所有子评论
            commentDao.deleteByRootId(id);
            // 删除评论
            Integer deleteNumber = commentDao.deleteById(id);
            if (deleteNumber == null || deleteNumber == 0) return false;
            else return true;
        }
    }

}
