package com.an.blog.bean;

import java.io.Serializable;

public class BlogTags implements Serializable {

    private Integer id;
    private Integer blogId;
    private Integer tagsId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getTagsId() {
        return tagsId;
    }

    public void setTagsId(Integer tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public String toString() {
        return "BlogTags{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", tagsId=" + tagsId +
                '}';
    }

}
