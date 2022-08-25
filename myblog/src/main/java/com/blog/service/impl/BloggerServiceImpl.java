package com.blog.service.impl;

import com.blog.mappers.BloggerMapper;
import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloggerServiceImpl implements BloggerService {
    @Autowired
    private BloggerMapper bloggerMapper;
    @Override
    public Blogger getByUserName(String username) {
        return bloggerMapper.getByUserName(username);
    }

    @Override
    public Integer updateUser(Blogger blogger) {
        SecurityUtils.getSubject().getSession().setAttribute("currentUser",blogger);
        return bloggerMapper.updateUser(blogger);
    }

    @Override
    public Blogger find() {
        return bloggerMapper.find();
    }
}
