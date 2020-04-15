package com.an.blog.controller;

import com.an.blog.bean.CommentLike;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.CommentLikeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @RequiresRoles("user")
    @PostMapping("/user/commentLike")
    public Result insertByTokenAndCommentLike(@RequestHeader("Authorization") String token, @RequestBody CommentLike commentLike) {
        Result result = new Result();

        if (!commentLikeService.insertByTokenAndCommentLike(token, commentLike)) {// 点赞失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @RequiresRoles("user")
    @DeleteMapping("/user/commentLike/{commentId}")
    public Result deleteByTokenAndCommentId(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) {
        Result result = new Result();

        if (!commentLikeService.deleteByTokenAndCommentId(token, commentId)) {// 删赞失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

}
