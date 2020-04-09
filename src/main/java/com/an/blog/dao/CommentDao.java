package com.an.blog.dao;

import com.an.blog.bean.Comment;
import com.an.blog.sqlProvider.CommentSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentDao {

    @Select("SELECT * FROM comment WHERE id = #{id}")
    @Results(id = "CommentResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "commentTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentDao.getChildTotalByRootId")),
            @Result(property = "commentList", column = "id", many = @Many(select = "com.an.blog.dao.CommentDao.getChildCommentListByRootId"))
    })
    public Comment getById(Integer id);

    @Select("SELECT * FROM comment WHERE blogId = #{blogId} AND rootId = 0 ORDER BY publishDate LIMIT #{start}, 10")
    @Results(id = "RootCommentResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "commentTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentDao.getChildTotalByRootId")),
            @Result(property = "commentList", column = "id", many = @Many(select = "com.an.blog.dao.CommentDao.getChildCommentListByRootId"))
    })
    public List<Comment> getRootCommentListByBlogIdAndStart(Integer blogId, Integer start);

    @Select("SELECT * FROM comment WHERE rootId = #{rootId} ORDER BY publishDate LIMIT #{start}, 10")
    @Results(id = "ChildCommentResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "dialogId", column = "dialogId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "dialog", column = "dialogId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById"))
    })
    public List<Comment> getChildCommentListByRootIdAndStart(Integer rootId, Integer start);

    @Select("SELECT * FROM comment WHERE rootId = #{rootId} ORDER BY publishDate LIMIT 0, 10")
    @ResultMap("ChildCommentResult")
    public List<Comment> getChildCommentListByRootId(Integer rootId);

    @Select("SELECT COUNT(*) FROM comment WHERE blogId = #{blogId}")
    public Integer getTotalByBlogId(Integer blogId);

    @Select("SELECT COUNT(*) FROM comment WHERE blogId = #{blogId} AND rootId = 0")
    public Integer getRootTotalByBlogId(Integer blogId);

    @Select("SELECT COUNT(*) FROM comment WHERE rootId = #{rootId}")
    public Integer getChildTotalByRootId(Integer rootId);

    @InsertProvider(type = CommentSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(Comment comment);

}
