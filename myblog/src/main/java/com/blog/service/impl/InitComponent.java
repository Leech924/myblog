package com.blog.service.impl;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Blogger;
import com.blog.entity.Link;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.blog.utils.Const;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将博客类型放入servletContext
        ServletContext servletContext = servletContextEvent.getServletContext();
        BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean(BlogTypeService.class);
//        BlogTypeService blogTypeService = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(BlogTypeService.class);
        List<BlogType> blogTypes = blogTypeService.queryForAll();
        servletContext.setAttribute(Const.BLOG_TYPE_LIST,blogTypes);

        //将博主信息放入servletContext中
        BloggerService bloggerService = (BloggerService)applicationContext.getBean(BloggerService.class);
        Blogger blogger = bloggerService.find();
        blogger.setPassword(null);
        servletContext.setAttribute(Const.BLOGGER,blogger);

        //将按年月划分的日期放入servletContext中
        BlogService blogService = applicationContext.getBean(BlogService.class);
        List<Blog> blogList = blogService.queryForAll();
        servletContext.setAttribute(Const.BLOG_COUNT_LIST,blogList);

        //将友情链接放入servletContext中
        LinkService linkService = applicationContext.getBean(LinkService.class);
        List<Link> linkList = linkService.list(null);
        servletContext.setAttribute(Const.LINK_LIST,linkList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
