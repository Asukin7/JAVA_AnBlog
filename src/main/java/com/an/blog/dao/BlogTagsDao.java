package com.an.blog.dao;

import com.an.blog.bean.Tags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogTagsDao {

    @Select("SELECT tags.* FROM blog_tags LEFT JOIN tags ON tags.id = blog_tags.tagsId WHERE blog_tags.blogId = #{blogId}")
    public List<Tags> getTagsListByBlogId(Integer blogId);

    @Insert({
            "<script>",
            "INSERT INTO blog_tags(blogId, tagsId) VALUES ",
            "<foreach collection='tagsIdList' item='item' index='index' separator=','>",
            "(#{blogId}, #{item})",
            "</foreach>",
            "</script>"
    })
    public Integer addByTagsIdListAndBlogId(List<Integer> tagsIdList, Integer blogId);

    @Delete("DELETE FROM blog_tags WHERE blogId = #{blogId}")
    public Integer deleteByBlogId(Integer blogId);

}
