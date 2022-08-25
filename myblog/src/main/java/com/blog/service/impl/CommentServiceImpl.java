package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.mappers.CommentMapper;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Integer add(Comment comment) {
        return commentMapper.add(comment);
    }

    @Override
    public Integer update(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentMapper.list(map);
    }

    @Override
    public Integer delete(Integer id) {
        return commentMapper.delete(id);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentMapper.getTotal(map);
    }
}
