package com.an.blog.controller;

import com.an.blog.bean.BlogLike;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.BlogLikeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogLikeController {

    @Autowired
    private BlogLikeService blogLikeService;

    @RequiresRoles("user")
    @PostMapping("/user/blogLike")
    public Result userInsertBlogLike(@RequestHeader("Authorization") String token, @RequestBody BlogLike blogLike) {
        Result result = new Result();

        if (!blogLikeService.insertByTokenAndBlogLike(token, blogLike)) {// 点赞失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @RequiresRoles("user")
    @DeleteMapping("/user/blogLike/{blogId}")
    public Result userDeleteBlogLike(@RequestHeader("Authorization") String token, @PathVariable Integer blogId) {
        Result result = new Result();

        if (!blogLikeService.deleteByTokenAndBlogId(token, blogId)) {// 删赞失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

}
