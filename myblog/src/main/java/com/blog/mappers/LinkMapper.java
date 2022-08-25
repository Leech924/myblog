package com.blog.mappers;

import com.blog.entity.Link;

import java.util.List;
import java.util.Map;

public interface LinkMapper {
    /**根据id查询指定友情链接*/
    public Link queryForId(Integer id);
    /**不固定参数查询友情链接列表*/
    public List<Link> list(Map<String,Object> paramMap);
    /**不固定参数查询友情链接数*/
    public Long getTotal(Map<String,Object> paramMap);
    /**添加一条友情链接*/
    public Integer insertLink(Link link);
    /**删除一条友情链接*/
    public Integer deleteLink(Integer id);
    /**修改指定友情链接*/
    public Integer updateLink(Link link);
}
