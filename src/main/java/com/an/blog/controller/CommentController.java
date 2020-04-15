package com.an.blog.controller;

import com.an.blog.bean.Comment;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.CommentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequiresRoles("user")
    @GetMapping("/user/rootCommentList/{blogId}")
    public Result userGetCommentListByBlogId(@RequestHeader("Authorization") String token, @PathVariable Integer blogId, @RequestParam Integer page) {
        Result result = new Result();

        // 获取评论列表
        List<Comment> commentList = commentService.getRootCommentListByTokenAndBlogIdAndPage(token, blogId, page);
        // 获取评论总数
        Integer commentTotal = commentService.getRootCommentTotalByBlogId(blogId);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("commentList", commentList);
        resultData.put("commentTotal", commentTotal);
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @GetMapping("/user/childCommentList/{rootId}")
    public Result userGetChildCommentListByBlogId(@RequestHeader("Authorization") String token, @PathVariable Integer rootId, @RequestParam Integer page) {
        Result result = new Result();

        // 获取评论列表
        List<Comment> commentList = commentService.getChildCommentListByTokenAndRootIdAndPage(token, rootId, page);
        // 获取评论总数
        Integer commentTotal = commentService.getChildCommentTotalByRootId(rootId);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("commentList", commentList);
        resultData.put("commentTotal", commentTotal);
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @PostMapping("/user/comment")
    public Result userInsertComment(@RequestHeader("Authorization") String token, @RequestBody Comment comment) {
        Result result = new Result();

        if (!commentService.insertByTokenAndComment(token, comment)) {// 保存评论失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        Comment commentById = commentService.getCommentById(comment.getId());

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("comment", commentById);
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @DeleteMapping("/user/comment/{id}")
    public Result userDeleteComment(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        Result result = new Result();

        if (!commentService.deleteByTokenAndId(token, id)) {// 删除失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @GetMapping("/tourist/rootCommentList/{blogId}")
    public Result touristGetCommentListByBlogId(@PathVariable Integer blogId, @RequestParam Integer page) {
        Result result = new Result();

        // 获取评论列表
        List<Comment> commentList = commentService.getRootCommentListByBlogIdAndPage(blogId, page);
        // 获取评论总数
        Integer commentTotal = commentService.getRootCommentTotalByBlogId(blogId);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("commentList", commentList);
        resultData.put("commentTotal", commentTotal);
        result.setData(resultData);
        return result;
    }

    @GetMapping("/tourist/childCommentList/{rootId}")
    public Result touristGetChildCommentListByBlogId(@PathVariable Integer rootId, @RequestParam Integer page) {
        Result result = new Result();

        // 获取评论列表
        List<Comment> commentList = commentService.getChildCommentListByRootIdAndPage(rootId, page);
        // 获取评论总数
        Integer commentTotal = commentService.getChildCommentTotalByRootId(rootId);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("commentList", commentList);
        resultData.put("commentTotal", commentTotal);
        result.setData(resultData);
        return result;
    }

}
