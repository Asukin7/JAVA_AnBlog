package com.an.blog.bean;

import java.io.Serializable;
import java.util.Date;

public class CommentLike implements Serializable {

    private Integer id;
    private Integer userId;
    private Integer blogId;
    private Integer commentId;
    private Integer rootId;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommentLike{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", commentId=" + commentId +
                ", rootId=" + rootId +
                ", date=" + date +
                '}';
    }

}
