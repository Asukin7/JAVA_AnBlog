package com.an.blog.controller;

import com.an.blog.bean.Tags;
import com.an.blog.common.Result;
import com.an.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/tourist/tagsList/rand")
    public Result touristGetTagsListByRand() {
        Result result = new Result();

        List<Tags> tagsList = tagsService.getListByRand();

        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("tagsList", tagsList);
        result.setData(resultData);
        return result;
    }

}
