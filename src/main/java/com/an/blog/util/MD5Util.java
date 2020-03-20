package com.an.blog.util;

import org.springframework.util.DigestUtils;

public class MD5Util {

    private static String salt = "ASuKiN7AwIys0ZanZxc";

    public static String getMD5(String str) {
        String base = str + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
