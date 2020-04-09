package com.an.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // !!!!!!此为开发路径 生产环境需要修改!!!!!!
        registry.addResourceHandler("/image/**").addResourceLocations("file:D:/test/AnBlog/image/");
    }

}
