package com.an.blog.dao;

import com.an.blog.bean.Blog;
import com.an.blog.sqlProvider.BlogSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BlogDao {

    @SelectProvider(type = BlogSqlProvider.class, method = "select")
    @Results(id = "BlogResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "userId"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "state", column = "state"),
            @Result(property = "title", column = "title"),
            @Result(property = "mdContent", column = "mdContent"),
            @Result(property = "htmlContent", column = "htmlContent"),
            @Result(property = "summary", column = "summary"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "editDate", column = "editDate"),
            @Result(property = "viewNumber", column = "viewNumber"),
            @Result(property = "tagsList", column = "id", many = @Many(select = "com.an.blog.dao.BlogTagsDao.getTagsListByBlogId")),
            @Result(property = "user", column = "userId", one = @One(select = "com.an.blog.dao.UserDao.getInfoById")),
            @Result(property = "category", column = "categoryId", one = @One(select = "com.an.blog.dao.CategoryDao.getById")),
            @Result(property = "commentNumber", column = "id", one = @One(select = "com.an.blog.dao.CommentDao.getTotalByBlogId"))
    })
    public List<Blog> select(Map<String, Object> map);

    @SelectProvider(type = BlogSqlProvider.class, method = "selectTotal")
    public Integer selectTotal(Map<String, Object> map);

    @Select("SELECT blog.* FROM blog LEFT JOIN blog_tags ON blog.id = blog_tags.blogId WHERE blog_tags.tagsId = #{tagsId} ORDER BY blog.editDate LIMIT #{start}, #{size}")
    @ResultMap("BlogResult")
    public List<Blog> getListByTagsIdMap(Map<String, Object> map);

    @Select("SELECT COUNT(*) FROM blog LEFT JOIN blog_tags ON blog.id = blog_tags.blogId WHERE blog_tags.tagsId = #{tagsId}")
    public Integer getTotalByTagsIdMap(Map<String, Object> map);

    @Select("SELECT * FROM blog WHERE id = #{id}")
    @ResultMap("BlogResult")
    public Blog getById(Integer id);

    @Select("SELECT * FROM blog WHERE id = #{id} AND userId = #{userId}")
    @ResultMap("BlogResult")
    public Blog getByIdAndUserId(Integer id, Integer userId);

    @InsertProvider(type = BlogSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public Integer insert(Blog blog);

    @UpdateProvider(type = BlogSqlProvider.class, method = "update")
    public Integer update(Blog blog);

    @Delete("DELETE FROM blog WHERE id = #{id}")
    public Integer deleteById(Integer id);

    @Update("UPDATE blog SET viewNumber = viewNumber + 1 WHERE id = #{id}")
    public Integer addViewNumberById(Integer id);

}
