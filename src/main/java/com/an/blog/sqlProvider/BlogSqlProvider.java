package com.an.blog.sqlProvider;

import com.an.blog.bean.Blog;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BlogSqlProvider {

    public String getListByMap(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("blog.*, (SELECT COUNT(*) FROM blog_like WHERE blog_like.blogId = blog.id) AS likeNumber");
                FROM("blog");
                if (map.get("tagsId") != null) {
                    LEFT_OUTER_JOIN("blog_tags ON blog.id = blog_tags.blogId");
                    WHERE("blog_tags.tagsId = #{tagsId}");
                }
                if (map.get("id") != null) WHERE("blog.id = #{id}");
                if (map.get("userId") != null) WHERE("blog.userId = #{userId}");
                if (map.get("categoryId") != null) WHERE("blog.categoryId = #{categoryId}");
                if (map.get("state") != null) WHERE("blog.state = #{state}");
                if (map.get("title") != null) WHERE("blog.title LIKE CONCAT('%', #{title}, '%')");
                if (map.get("mdContent") != null) WHERE("blog.mdContent LIKE CONCAT('%', #{mdContent}, '%')");
                if (map.get("htmlContent") != null) WHERE("blog.htmlContent LIKE CONCAT('%', #{htmlContent}, '%')");
                if (map.get("summary") != null) WHERE("blog.summary LIKE CONCAT('%', #{summary}, '%')");
                if (map.get("publishDate") != null) WHERE("blog.publishDate = #{publishDate}");
                if (map.get("editDate") != null) WHERE("blog.editDate = #{editDate}");
                if (map.get("viewNumber") != null) WHERE("blog.viewNumber = #{viewNumber}");
                if (map.get("orderLikeNumber") != null) ORDER_BY("likeNumber DESC", "editDate DESC");
                else ORDER_BY("editDate DESC");
                LIMIT("#{start}, #{size}");
            }
        }.toString();
        return queryStr;
    }

    public String getTotalByMap(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("COUNT(*)");
                FROM("blog");
                if (map.get("tagsId") != null) {
                    LEFT_OUTER_JOIN("blog_tags ON blog.id = blog_tags.blogId");
                    WHERE("blog_tags.tagsId = #{tagsId}");
                }
                if (map.get("id") != null) WHERE("blog.id = #{id}");
                if (map.get("userId") != null) WHERE("blog.userId = #{userId}");
                if (map.get("categoryId") != null) WHERE("blog.categoryId = #{categoryId}");
                if (map.get("state") != null) WHERE("blog.state = #{state}");
                if (map.get("title") != null) WHERE("blog.title LIKE CONCAT('%', #{title}, '%')");
                if (map.get("mdContent") != null) WHERE("blog.mdContent LIKE CONCAT('%', #{mdContent}, '%')");
                if (map.get("htmlContent") != null) WHERE("blog.htmlContent LIKE CONCAT('%', #{htmlContent}, '%')");
                if (map.get("summary") != null) WHERE("blog.summary LIKE CONCAT('%', #{summary}, '%')");
                if (map.get("publishDate") != null) WHERE("blog.publishDate = #{publishDate}");
                if (map.get("editDate") != null) WHERE("blog.editDate = #{editDate}");
                if (map.get("viewNumber") != null) WHERE("blog.viewNumber = #{viewNumber}");
            }
        }.toString();
        return queryStr;
    }

    public String insert(Blog blog) {
        String queryStr = new SQL() {
            {
                INSERT_INTO("blog");
                if (blog.getUserId() != null) VALUES("userId", "#{userId}");
                if (blog.getCategoryId() != null) VALUES("categoryId", "#{categoryId}");
                if (blog.getState() != null) VALUES("state", "#{state}");
                if (blog.getTitle() != null) VALUES("title", "#{title}");
                if (blog.getMdContent() != null) VALUES("mdContent", "#{mdContent}");
                if (blog.getHtmlContent() != null) VALUES("htmlContent", "#{htmlContent}");
                if (blog.getSummary() != null) VALUES("summary", "#{summary}");
                if (blog.getPublishDate() != null) VALUES("publishDate", "#{publishDate}");
                if (blog.getEditDate() != null) VALUES("editDate", "#{editDate}");
                if (blog.getViewNumber() != null) VALUES("viewNumber", "#{viewNumber}");
            }
        }.toString();
        return queryStr;
    }

    public String update(Blog blog) {
        String queryStr = new SQL() {
            {
                UPDATE("blog");
                if (blog.getCategoryId() != null) SET("categoryId = #{categoryId}");
                if (blog.getState() != null) SET("state = #{state}");
                if (blog.getTitle() != null) SET("title = #{title}");
                if (blog.getMdContent() != null) SET("mdContent = #{mdContent}");
                if (blog.getHtmlContent() != null) SET("htmlContent = #{htmlContent}");
                if (blog.getSummary() != null) SET("summary = #{summary}");
                if (blog.getPublishDate() != null) SET("publishDate = #{publishDate}");
                if (blog.getEditDate() != null) SET("editDate = #{editDate}");
                if (blog.getViewNumber() != null) SET("viewNumber = #{viewNumber}");
                WHERE("id = #{id}");
                WHERE("userId = #{userId}");
            }
        }.toString();
        return queryStr;
    }

}
