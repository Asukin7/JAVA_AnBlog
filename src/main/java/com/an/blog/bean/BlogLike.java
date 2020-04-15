package com.an.blog.bean;

import java.io.Serializable;
import java.util.Date;

public class BlogLike implements Serializable {

    private Integer id;
    private Integer userId;
    private Integer blogId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BlogLike{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", date=" + date +
                '}';
    }

}
