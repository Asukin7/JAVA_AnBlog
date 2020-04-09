package com.an.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable {

    private Integer id;
    private Integer userId;
    private Integer blogId;
    private Integer rootId;
    private Integer dialogId;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Shanghai")
    private Date publishDate;
    private User user;
    private User dialog;
    private Integer commentTotal;
    private List<Comment> commentList;

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

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getDialogId() {
        return dialogId;
    }

    public void setDialogId(Integer dialogId) {
        this.dialogId = dialogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDialog() {
        return dialog;
    }

    public void setDialog(User dialog) {
        this.dialog = dialog;
    }

    public Integer getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(Integer commentTotal) {
        this.commentTotal = commentTotal;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", rootId=" + rootId +
                ", dialogId=" + dialogId +
                ", content='" + content + '\'' +
                ", publishDate=" + publishDate +
                ", user=" + user +
                ", dialog=" + dialog +
                ", commentTotal=" + commentTotal +
                ", commentList=" + commentList +
                '}';
    }

}
