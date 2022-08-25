package com.blog.service;

import com.blog.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**添加一条评论*/
    public Integer add(Comment comment);
    /**更新一条评论*/
    public Integer update(Comment comment);
    /**查询评论*/
    public List<Comment> list(Map<String,Object> map);
    /**删除一条评论*/
    public Integer delete(Integer id);
    /**查询评论数量*/
    public Long getTotal(Map<String,Object> map);
}
