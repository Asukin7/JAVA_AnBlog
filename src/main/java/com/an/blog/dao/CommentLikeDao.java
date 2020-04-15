package com.an.blog.dao;

import com.an.blog.bean.CommentLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface CommentLikeDao {

    @Select("SELECT COUNT(*) FROM comment_like WHERE commentId = #{commentId}")
    public Integer getTotalByCommentId(Integer commentId);

    @Insert("INSERT INTO comment_like(userId, blogId, commentId, rootId, date) VALUES(#{userId}, #{blogId}, #{commentId}, #{rootId}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(CommentLike commentLike);

    @Delete("DELETE FROM comment_like WHERE blogId = #{blogId}")
    public Integer deleteByBlogId(Integer blogId);

    @Delete("DELETE FROM comment_like WHERE commentId = #{commentId}")
    public Integer deleteByCommentId(Integer commentId);

    @Delete("DELETE FROM comment_like WHERE rootId = #{rootId}")
    public Integer deleteByRootId(Integer rootId);

    @Delete("DELETE FROM comment_like WHERE userId = #{userId} AND commentId = #{commentId}")
    public Integer deleteByUserIdAndCommentId(Integer userId, Integer commentId);

}
