package com.an.blog.service.impl;

import com.an.blog.bean.Tags;
import com.an.blog.dao.TagsDao;
import com.an.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagsService")
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsDao tagsDao;

    @Override
    public List<Tags> getListByRand() {
        return tagsDao.getListByRand();
    }

}
