package com.an.blog.controller;

import com.an.blog.bean.Blog;
import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import com.an.blog.service.BlogService;
import com.an.blog.util.TokenUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequiresRoles("user")
    @GetMapping("/user/blog/{id}")
    public Result userGetBlog(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        Result result = new Result();

        // 获取文章
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {// 文章不存在
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        if (blog.getUserId() != TokenUtil.getTokenData(token, "id").asInt()) {// 操作用户不是文章作者
            if (blog.getState() != 1) {// 文章未发布
                result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
                return result;
            } else {
                // 阅读数加一
                blogService.addBlogViewNumberById(id);
                blog.setViewNumber(blog.getViewNumber() + 1);
            }
        }

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("blog", blog);
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @GetMapping("/user/blogList")
    public Result userGetBlogList(@RequestHeader("Authorization") String token, @RequestParam Map<String, Object> map) {
        Result result = new Result();

        // 获取文章列表
        List<Blog> blogList = blogService.getBlogListByTokenAndMap(token, map);
        // 获取文章总数
        Integer blogTotal = blogService.getBlogTotalByTokenAndMap(token, map);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("blogList", blogList);
        resultData.put("blogTotal", blogTotal);
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @PostMapping("/user/blog")
    public Result userInsertBlog(@RequestHeader("Authorization") String token, @RequestBody Blog blog) {
        Result result = new Result();

        if (!blogService.saveBlogByTokenAndBlog(token, blog)) {// 保存文章失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("blogId", blog.getId());
        result.setData(resultData);
        return result;
    }

    @RequiresRoles("user")
    @PutMapping("/user/blog")
    public Result userUpdateBlog(@RequestHeader("Authorization") String token, @RequestBody Blog blog) {
        Result result = new Result();

        if (!blogService.saveBlogByTokenAndBlog(token, blog)) {// 保存文章失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @RequiresRoles("user")
    @DeleteMapping("/user/blog/{id}")
    public Result userDeleteBlog(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        Result result = new Result();

        if (!blogService.deleteBlogByTokenAndId(token, id)) {// 删除文章失败
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        return result;
    }

    @GetMapping("/tourist/blog/{id}")
    public Result touristGetBlog(@PathVariable Integer id) {
        Result result = new Result();

        // 获取文章
        Blog blog = blogService.getBlogById(id);
        if (blog == null) {// 文章不存在
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }
        if (blog.getState() != 1) {// 文章未发布
            result.setResultStatus(ResultStatus.UNKNOWN);//需要修改------需要修改------需要修改------需要修改------需要修改------需要修改
            return result;
        }

        // 阅读数加一
        blogService.addBlogViewNumberById(id);
        blog.setViewNumber(blog.getViewNumber() + 1);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("blog", blog);
        result.setData(resultData);
        return result;
    }

    @GetMapping("/tourist/blogList")
    public Result touristGetBlogList(@RequestParam Map<String, Object> map) {
        Result result = new Result();

        // 设置查找已发表的文章
        map.put("state", 1);
        // 获取文章列表
        List<Blog> blogList = blogService.getBlogListByMap(map);
        // 获取文章总数
        Integer blogTotal = blogService.getBlogTotalByMap(map);

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("blogList", blogList);
        resultData.put("blogTotal", blogTotal);
        result.setData(resultData);
        return result;
    }

}
