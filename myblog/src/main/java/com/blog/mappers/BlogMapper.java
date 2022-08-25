package com.blog.mappers;

import com.blog.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    /** 无参数查询博客列表*/
    public List<Blog> queryForAll();
    /** 有参数查询博客列表*/
    public List<Blog> list(Map<String,Object> map);
    /** 有参数查询博客数量*/
    public Long getTotal(Map<String,Object> map);
    /** 根据id查询博客*/
    public Blog queryForId(Integer id);
    /** 插入博客*/
    public Integer insertBlog(Blog blog);
    /** 删除博客*/
    public Integer deleteBlog(Integer id);
    /** 修改博客*/
    public Integer updateBlog(Blog blog);
    /**根据类型查询博客数量*/
    public Integer queryBlogByBlogType(Integer typeId);
    /** 上一篇*/
    public Blog getLastBlog(Integer id);
    /** 下一篇*/
    public Blog getNextBlog(Integer id);

}
