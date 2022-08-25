package com.blog.service;

import com.blog.entity.BlogType;

import java.util.List;
import java.util.Map;

public interface BlogTypeService {
    /**无参数查询所有博客类型的记录*/
    public List<BlogType> queryForAll();
    /**根据id查询指定博客类型*/
    public BlogType queryForId(Integer id);
    /**不固定参数查询博客类型列表*/
    public List<BlogType> queryForClass(Map<String,Object> paramMap);
    /**不固定参数查询博客类型数*/
    public Long getTotal(Map<String,Object> paramMap);
    /**添加一条博客类型*/
    public Integer insertBlogType(BlogType blogType);
    /**删除一条博客类型*/
    public Integer deleteBlogType(Integer id);
    /**修改指定博客类型*/
    public Integer updateBlogType(BlogType blogType);
}
