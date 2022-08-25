package com.blog.service.impl;

import com.blog.entity.BlogType;
import com.blog.mappers.BlogTypeMapper;
import com.blog.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class BlogTypeServiceImpl implements BlogTypeService {
    @Autowired
    private BlogTypeMapper  blogTypeMapper;
    @Override
    public List<BlogType> queryForAll() {
        return blogTypeMapper.queryForAll();
    }

    @Override
    public BlogType queryForId(Integer id) {
        return blogTypeMapper.queryForId(id);
    }

    @Override
    public List<BlogType> queryForClass(Map<String, Object> paramMap) {
        return blogTypeMapper.queryForClass(paramMap);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return blogTypeMapper.getTotal(paramMap);
    }

    @Override
    public Integer insertBlogType(BlogType blogType) {
        return blogTypeMapper.insertBlogType(blogType);
    }

    @Override
    public Integer deleteBlogType(Integer id) {
        return blogTypeMapper.deleteBlogType(id);
    }

    @Override
    public Integer updateBlogType(BlogType blogType) {
        return blogTypeMapper.updateBlogType(blogType);
    }
}
