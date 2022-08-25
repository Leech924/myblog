package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Blogger;
import com.blog.entity.Link;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.blog.utils.Const;
import com.blog.utils.ResponseUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {
    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BloggerService bloggerService;
    @Autowired
    private LinkService linkService;
    /**刷新系统缓存*/
    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取ServletContext
        ServletContext servletContext = RequestContextUtils.getWebApplicationContext(request).getServletContext();
        //博客类别
        List<BlogType> blogTypes = blogTypeService.queryForAll();
        servletContext.setAttribute(Const.BLOG_TYPE_LIST,blogTypes);
        //博主信息
        Blogger blogger = bloggerService.find();
        blogger.setPassword(null);
        servletContext.setAttribute(Const.BLOGGER,blogger);
        //将按年月划分的日期放入servletContext中
        List<Blog> blogList = blogService.queryForAll();
        servletContext.setAttribute(Const.BLOG_COUNT_LIST,blogList);
        //将友情链接放入servletContext中
        List<Link> linkList = linkService.list(null);
        servletContext.setAttribute(Const.LINK_LIST,linkList);
        //返回数据
        JSONObject result = new JSONObject();
        result.put("success",Boolean.TRUE);
        ResponseUtil.write(response,result);
        return null;

    }
}
