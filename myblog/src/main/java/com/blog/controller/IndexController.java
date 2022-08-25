package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.utils.PageUtil;
import com.blog.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @RequestMapping("/index")
    public ModelAndView index(ModelAndView mav,
                              @RequestParam(value = "page",required = false) String page,
                              HttpServletResponse response,
                              HttpServletRequest request,
                              @RequestParam(value = "typeId",required = false) String typeId,
                              @RequestParam(value = "releaseDateStr",required = false)String releaseDateStr){
        if (StringUtil.isEmpty(page)){
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page),10);
        HashMap<String, Object> map = new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        map.put("typeId",typeId);
        map.put("releaseDateStr",releaseDateStr);
        List<Blog> list = blogService.list(map);
        StringBuffer param = new StringBuffer();
        if(!StringUtil.isEmpty(typeId)){
            param.append("typeId=" + typeId + "&");
        }
        if (!StringUtil.isEmpty(releaseDateStr)){
            param.append("releaseDate=" + releaseDateStr +"&");
        }
        mav.addObject("mainPage","/foreground/blog/list.jsp");
        String pageCode = PageUtil.getPagination(request.getContextPath() + "/index", blogService.getTotal(map), Integer.parseInt(page), 10, param.toString());
        mav.addObject("pageCode",pageCode);
        mav.addObject("blogList",list);
        return mav;
    }
}
