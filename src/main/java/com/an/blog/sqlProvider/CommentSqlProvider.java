package com.an.blog.sqlProvider;

import com.an.blog.bean.Comment;
import org.apache.ibatis.jdbc.SQL;

public class CommentSqlProvider {

    public String insert(Comment comment) {
        String queryStr = new SQL() {
            {
                INSERT_INTO("comment");
                if (comment.getUserId() != null) VALUES("userId", "#{userId}");
                if (comment.getBlogId() != null) VALUES("blogId", "#{blogId}");
                if (comment.getRootId() != null) VALUES("rootId", "#{rootId}");
                if (comment.getDialogId() != null) VALUES("dialogId", "#{dialogId}");
                if (comment.getContent() != null) VALUES("content", "#{content}");
                if (comment.getPublishDate() != null) VALUES("publishDate", "#{publishDate}");
            }
        }.toString();
        return queryStr;
    }

}
