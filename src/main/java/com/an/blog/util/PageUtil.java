package com.an.blog.util;

public class PageUtil {

    public static Integer getStart(Integer page, Integer size) {
        return (page - 1) * size;
    }

}
