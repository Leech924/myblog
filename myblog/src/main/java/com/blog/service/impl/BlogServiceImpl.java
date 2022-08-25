package com.blog.service.impl;

import com.blog.entity.Blog;
import com.blog.mappers.BlogMapper;
import com.blog.mappers.CommentMapper;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Blog> queryForAll() {
        return blogMapper.queryForAll();

    }

    @Override
    public List<Blog> list(Map<String, Object> map) {
        return blogMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogMapper.getTotal(map);
    }

    @Override
    public Blog queryForId(Integer id) {
        return blogMapper.queryForId(id);
    }

    @Override
    public Integer insertBlog(Blog blog) {
        return blogMapper.insertBlog(blog);
    }

    @Override
    public Integer deleteBlog(Integer id) {
        commentMapper.deleteForBlogId(id);
        return blogMapper.deleteBlog(id);
    }

    @Override
    public Integer updateBlog(Blog blog) {
        return blogMapper.updateBlog(blog);
    }

    @Override
    public Integer queryBlogByBlogType(Integer typeId) {
        return blogMapper.queryBlogByBlogType(typeId);
    }

    @Override
    public Blog getLastBlog(Integer id) {
        return blogMapper.getLastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogMapper.getNextBlog(id);
    }
}
