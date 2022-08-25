package com.blog.service;

import com.blog.entity.Blogger;

public interface BloggerService {
    public Blogger getByUserName(String username);
    /**更新用户信息*/
    public Integer updateUser(Blogger blogger);
//    查询博主信息
    public Blogger find();
}
