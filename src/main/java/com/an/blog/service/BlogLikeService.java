package com.an.blog.service;

import com.an.blog.bean.BlogLike;

public interface BlogLikeService {

    public boolean insertByTokenAndBlogLike(String token, BlogLike blogLike);

    public boolean deleteByTokenAndBlogId(String token, Integer blogId);

}
