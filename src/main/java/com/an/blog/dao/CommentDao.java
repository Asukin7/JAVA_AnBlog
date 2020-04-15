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
            @Result(property = "dialogId", column = "dialogId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "dialog", column = "dialogId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "commentTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentDao.getChildTotalByRootId")),
            @Result(property = "likeTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentLikeDao.getTotalByCommentId")),
            @Result(property = "commentList", column = "id", many = @Many(select = "com.an.blog.dao.CommentDao.getChildCommentListByRootId"))
    })
    public Comment getById(Integer id);

    @Select("SELECT *, (SELECT COUNT(*) FROM comment_like WHERE comment_like.commentId = comment.id) AS likeTotal FROM comment WHERE blogId = #{blogId} AND rootId = 0 ORDER BY likeTotal DESC, publishDate LIMIT #{start}, 10")
    @Results(id = "RootCommentResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "commentTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentDao.getChildTotalByRootId")),
            @Result(property = "commentList", column = "id", many = @Many(select = "com.an.blog.dao.CommentDao.getChildCommentListByRootId"))
    })
    public List<Comment> getRootCommentListByBlogIdAndStart(Integer blogId, Integer start);

    @Select("SELECT *, (SELECT COUNT(*) FROM comment_like WHERE comment_like.commentId = comment.id) AS likeTotal FROM comment WHERE rootId = #{rootId} ORDER BY likeTotal DESC, publishDate LIMIT #{start}, 10")
    @Results(id = "ChildCommentResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "dialogId", column = "dialogId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "dialog", column = "dialogId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById"))
    })
    public List<Comment> getChildCommentListByRootIdAndStart(Integer rootId, Integer start);

    @Select("SELECT *, (SELECT COUNT(*) FROM comment_like WHERE comment_like.commentId = comment.id) AS likeTotal FROM comment WHERE rootId = #{rootId} ORDER BY likeTotal DESC, publishDate LIMIT 0, 10")
    @ResultMap("ChildCommentResult")
    public List<Comment> getChildCommentListByRootId(Integer rootId);

    @Select("SELECT comment.*, (SELECT COUNT(*) FROM comment_like WHERE comment_like.commentId = comment.id) AS likeTotal, comment_like.id IS NOT NULL AS likeAction, #{userId} AS currentUserId \n" +
            "FROM comment \n" +
            "LEFT OUTER JOIN comment_like ON comment.id = comment_like.commentId AND comment_like.userId = #{userId} \n" +
            "WHERE comment.blogId = #{blogId} AND comment.rootId = 0 ORDER BY likeTotal DESC, publishDate LIMIT #{start}, 10")
    @Results(id = "RootCommentResult1", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "commentTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentDao.getChildTotalByRootId")),
            @Result(property = "likeTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentLikeDao.getTotalByCommentId")),
            @Result(property = "commentList", column = "{ rootId = id, userId = currentUserId }", many = @Many(select = "com.an.blog.dao.CommentDao.getChildCommentListByUserIdAndRootId"))
    })
    public List<Comment> getRootCommentListByUserIdAndBlogIdAndStart(Integer userId, Integer blogId, Integer start);

    @Select("SELECT comment.*, (SELECT COUNT(*) FROM comment_like WHERE comment_like.commentId = comment.id) AS likeTotal, comment_like.id IS NOT NULL AS likeAction \n" +
            "FROM comment \n" +
            "LEFT OUTER JOIN comment_like ON comment.id = comment_like.commentId AND comment_like.userId = #{userId} \n" +
            "WHERE comment.rootId = #{rootId} ORDER BY likeTotal DESC, publishDate LIMIT #{start}, 10")
    @Results(id = "ChildCommentResult1", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "dialogId", column = "dialogId"),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "dialog", column = "dialogId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "likeTotal", column = "id", one = @One(select = "com.an.blog.dao.CommentLikeDao.getTotalByCommentId"))
    })
    public List<Comment> getChildCommentListByUserIdAndRootIdAndStart(Integer userId, Integer rootId, Integer start);

    @Select("SELECT comment.*, (SELECT COUNT(*) FROM comment_like WHERE comment_like.commentId = comment.id) AS likeTotal, comment_like.id IS NOT NULL AS likeAction \n" +
            "FROM comment \n" +
            "LEFT OUTER JOIN comment_like ON comment.id = comment_like.commentId AND comment_like.userId = #{userId} \n" +
            "WHERE comment.rootId = #{rootId} ORDER BY likeTotal DESC, publishDate LIMIT 0, 10")
    @ResultMap("ChildCommentResult1")
    public List<Comment> getChildCommentListByUserIdAndRootId(Integer userId, Integer rootId);

    @Select("SELECT COUNT(*) FROM comment WHERE blogId = #{blogId}")
    public Integer getTotalByBlogId(Integer blogId);

    @Select("SELECT COUNT(*) FROM comment WHERE blogId = #{blogId} AND rootId = 0")
    public Integer getRootTotalByBlogId(Integer blogId);

    @Select("SELECT COUNT(*) FROM comment WHERE rootId = #{rootId}")
    public Integer getChildTotalByRootId(Integer rootId);

    @InsertProvider(type = CommentSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(Comment comment);

    @Delete("DELETE FROM comment WHERE id = #{id}")
    public Integer deleteById(Integer id);

    @Delete("DELETE FROM comment WHERE blogId = #{blogId}")
    public Integer deleteByBlogId(Integer blogId);

    @Delete("DELETE FROM comment WHERE rootId = #{rootId}")
    public Integer deleteByRootId(Integer rootId);

}
