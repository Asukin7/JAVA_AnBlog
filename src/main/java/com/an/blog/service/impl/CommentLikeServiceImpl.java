package com.an.blog.service.impl;

import com.an.blog.bean.CommentLike;
import com.an.blog.dao.CommentLikeDao;
import com.an.blog.service.CommentLikeService;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("commentLikeService")
public class CommentLikeServiceImpl implements CommentLikeService {

    @Autowired
    private CommentLikeDao commentLikeDao;

    @Override
    public boolean insertByTokenAndCommentLike(String token, CommentLike commentLike) {
        // 获取用户id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId != null) commentLike.setUserId(userId);
        else return false;// userId为空 错误

        // 检查rootId
        if (commentLike.getRootId() == null) commentLike.setRootId(0);
        // 获取当前日期
        commentLike.setDate(new Date());

        // 删除该用户对当前评论的赞
        commentLikeDao.deleteByUserIdAndCommentId(userId, commentLike.getCommentId());
        // 添加该用户对当前评论的赞
        Integer insertNumber = commentLikeDao.insert(commentLike);
        if (insertNumber == null || insertNumber == 0) return false;
        else return true;
    }

    @Override
    public boolean deleteByTokenAndCommentId(String token, Integer CommentId) {
        // 获取用户id
        Integer userId = TokenUtil.getTokenData(token, "id").asInt();// 获取userId
        if (userId == null) return false;// userId为空 错误

        // 删除该用户对当前评论的赞
        Integer deleteNumber = commentLikeDao.deleteByUserIdAndCommentId(userId, CommentId);
        if (deleteNumber == null || deleteNumber == 0) return false;
        else return true;
    }

}
