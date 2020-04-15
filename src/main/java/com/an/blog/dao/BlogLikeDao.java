package com.an.blog.dao;

import com.an.blog.bean.BlogLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface BlogLikeDao {

    @Select("SELECT COUNT(*) FROM blog_like WHERE blogId = #{blogId}")
    public Integer getTotalByBlogId(Integer blogId);

    @Insert("INSERT INTO blog_like(userId, blogId, date) VALUES(#{userId}, #{blogId}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(BlogLike blogLike);

    @Delete("DELETE FROM blog_like WHERE blogId = #{blogId}")
    public Integer deleteByBlogId(Integer blogId);

    @Delete("DELETE FROM blog_like WHERE userId = #{userId} AND blogId = #{blogId}")
    public Integer deleteByUserIdAndBlogId(Integer userId, Integer blogId);

}
