package com.blog.mappers;

import com.blog.entity.Blogger;
import org.apache.ibatis.annotations.Param;

/**
 * 博主
 */
public interface BloggerMapper {
    /**根据用户名查询用户*/
    public Blogger getByUserName(@Param("username") String username);
    /**更新用户信息*/
    public Integer updateUser(Blogger blogger);
    /**查询博主*/
    public Blogger find();
}
