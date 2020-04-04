package com.an.blog.sqlProvider;

import com.an.blog.bean.Blog;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BlogSqlProvider {

    public String select(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("*");
                FROM("blog");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("categoryId") != null) WHERE("categoryId = #{categoryId}");
                if (map.get("state") != null) WHERE("state = #{state}");
                if (map.get("title") != null) WHERE("title LIKE CONCAT('%', #{title}, '%')");
                if (map.get("mdContent") != null) WHERE("mdContent LIKE CONCAT('%', #{mdContent}, '%')");
                if (map.get("htmlContent") != null) WHERE("htmlContent LIKE CONCAT('%', #{htmlContent}, '%')");
                if (map.get("summary") != null) WHERE("summary LIKE CONCAT('%', #{summary}, '%')");
                if (map.get("publishDate") != null) WHERE("publishDate = #{publishDate}");
                if (map.get("editDate") != null) WHERE("editDate = #{editDate}");
                if (map.get("viewNumber") != null) WHERE("viewNumber = #{viewNumber}");
                ORDER_BY("editDate DESC");
                LIMIT("#{start}, #{size}");
            }
        }.toString();
        return queryStr;
    }

    public String selectTotal(Map<String, Object> map) {
        String queryStr = new SQL() {
            {
                SELECT("COUNT(*)");
                FROM("blog");
                if (map.get("id") != null) WHERE("id = #{id}");
                if (map.get("userId") != null) WHERE("userId = #{userId}");
                if (map.get("categoryId") != null) WHERE("categoryId = #{categoryId}");
                if (map.get("state") != null) WHERE("state = #{state}");
                if (map.get("title") != null) WHERE("title LIKE CONCAT('%', #{title}, '%')");
                if (map.get("mdContent") != null) WHERE("mdContent LIKE CONCAT('%', #{mdContent}, '%')");
                if (map.get("htmlContent") != null) WHERE("htmlContent LIKE CONCAT('%', #{htmlContent}, '%')");
                if (map.get("summary") != null) WHERE("summary LIKE CONCAT('%', #{summary}, '%')");
                if (map.get("publishDate") != null) WHERE("publishDate = #{publishDate}");
                if (map.get("editDate") != null) WHERE("editDate = #{editDate}");
                if (map.get("viewNumber") != null) WHERE("viewNumber = #{viewNumber}");
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
