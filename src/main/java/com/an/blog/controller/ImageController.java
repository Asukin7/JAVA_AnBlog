package com.an.blog.controller;

import com.an.blog.common.Result;
import com.an.blog.common.ResultStatus;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ImageController {

    @RequiresRoles("user")
    @PostMapping("/user/image/upload")
    public Result uploadImg(HttpServletRequest req, MultipartFile image) {
        String imgRelativePath = "/image/" + new SimpleDateFormat("yyyyMMdd").format(new Date());// 文件存储相对路径
        String imgGetFullPath = "./AnBlog" + imgRelativePath;// 文件存储绝对路径

        File imgFolder = new File(imgGetFullPath);// 文件存储文件夹
        if (!imgFolder.exists()) {// 如果文件夹不存在
            imgFolder.mkdirs();// 则创建新的文件夹
        }

        StringBuffer imgUrl = new StringBuffer();// 文件访问地址
//        imgUrl.append(req.getScheme())// 开发环境
//                .append("://")
//                .append(req.getServerName())// 服务地址
//                .append(":")
//                .append(req.getServerPort())// 服务端口
//                .append(req.getContextPath())// 存储路径
//                .append(imgRelativePath);// 文件路径
        imgUrl.append("https://nnsststt.cn")// 生产环境
                .append(req.getContextPath())// 存储路径
                .append(imgRelativePath);// 文件路径

        String imgName = UUID.randomUUID().toString().replace("-", "") + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));// 文件名

        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));// 写入文件
            imgUrl.append("/").append(imgName);// 文件访问地址

            Result result = new Result();
            Map<String, Object> resultData = new HashMap<String, Object>();
            resultData.put("imgUrl", imgUrl);
            result.setData(resultData);
            return result;// 成功
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Result(ResultStatus.UNKNOWN);// 失败
    }

}
