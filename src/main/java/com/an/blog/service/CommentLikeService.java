package com.an.blog.service;

import com.an.blog.bean.CommentLike;

public interface CommentLikeService {

    public boolean insertByTokenAndCommentLike(String token, CommentLike commentLike);

    public boolean deleteByTokenAndCommentId(String token, Integer CommentId);

}
