package com.an.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Blog implements Serializable {

    private Integer id;
    private Integer userId;
    private Integer categoryId;
    private Integer state;
    private String title;
    private String mdContent;
    private String htmlContent;
    private String summary;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Shanghai")
    private Date publishDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="Asia/Shanghai")
    private Date editDate;
    private Integer viewNumber;
    private Integer commentNumber;
    private List<Tags> tagsList;
    private User user;
    private Category category;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMdContent() {
        return mdContent;
    }

    public void setMdContent(String mdContent) {
        this.mdContent = mdContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Integer getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(Integer viewNumber) {
        this.viewNumber = viewNumber;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", state=" + state +
                ", title='" + title + '\'' +
                ", mdContent='" + mdContent + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", summary='" + summary + '\'' +
                ", publishDate=" + publishDate +
                ", editDate=" + editDate +
                ", viewNumber=" + viewNumber +
                ", commentNumber=" + commentNumber +
                ", tagsList=" + tagsList +
                ", user=" + user +
                ", category=" + category +
                '}';
    }

}
