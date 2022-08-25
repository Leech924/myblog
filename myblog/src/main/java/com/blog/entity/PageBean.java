package com.blog.entity;

public class PageBean {
    /**当前第几页，从1开始*/
    private Integer page;
    /**页面大小*/
    private Integer pageSize;
    /**当前页从哪条记录开始*/
    private Integer start;



    public Integer getStart() {
        return (this.page - 1)*pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageBean(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;

    }

    public PageBean() {
    }


}
