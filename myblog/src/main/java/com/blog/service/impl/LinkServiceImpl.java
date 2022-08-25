package com.blog.service.impl;

import com.blog.entity.Link;
import com.blog.mappers.LinkMapper;
import com.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkMapper linkMapper;
    @Override
    public Link queryForId(Integer id) {
        return linkMapper.queryForId(id);
    }

    @Override
    public List<Link> list(Map<String, Object> map) {
        return linkMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return linkMapper.getTotal(map);
    }

    @Override
    public Integer insertLink(Link link) {
        return linkMapper.insertLink(link);
    }

    @Override
    public Integer deleteLink(Integer id) {
        return linkMapper.deleteLink(id);
    }

    @Override
    public Integer updateLink(Link link) {
        return linkMapper.updateLink(link);
    }
}
