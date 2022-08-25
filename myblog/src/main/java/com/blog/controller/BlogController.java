package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.utils.Const;
import com.blog.utils.StringUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller()
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    private BlogIndex blogIndex = new BlogIndex();

    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id,
                                HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        Blog blog = blogService.queryForId(id);
        mav.addObject("blog",blog);
        blog.setClickHit(blog.getClickHit()+1);
        blogService.updateBlog(blog);
        mav.addObject("mainPage","/foreground/blog/view.jsp");
        mav.addObject("pageTitle",blog.getTitle()+"_个人博客系统");
        mav.setViewName("index");
        //上一篇下一篇
        mav.addObject("pageCode",getPageCode(blogService.getLastBlog(id),
                blogService.getNextBlog(id),
                request.getServletContext().getContextPath()));
        //查询评论
        HashMap<String, Object> map = new HashMap<>();
        map.put("blogId",blog.getId());
        map.put("state",1);
        List<Comment> commentList = commentService.list(map);
        mav.addObject("commentList",commentList);
        //关键字
        String keyWord = blog.getKeyWord();
        if (StringUtil.isEmpty(keyWord)){
            mav.addObject("keyWords",null);
        }else {
            String[] s = keyWord.split(" ");
            List<String> list = StringUtil.filterWhite(Arrays.asList(s));
            mav.addObject("keyWords",list);
        }
        return mav;
    }

    /**
     * 上一篇下一篇
     * @return
     */
    private String getPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
        StringBuffer pageCode = new StringBuffer();
        if(lastBlog!=null&&lastBlog.getId()!=null){
            pageCode.append("<p>上一篇:<a href='"+projectContext + "/blog/articles/" + lastBlog.getId()+"'>"
            +lastBlog.getTitle() +"</a></p>");
        }else {
            pageCode.append("<p>上一篇:没有了</p>");
        }
        if(nextBlog!=null&&nextBlog.getId()!=null){
            pageCode.append("<p>下一篇:<a href='"+projectContext + "/blog/articles/" + nextBlog.getId()+"'>"
                    +nextBlog.getTitle() +"</a></p>");
        }else {
            pageCode.append("<p>下一篇:没有了</p>");
        }
        return pageCode.toString();
    }
    /**
     * 根据关键字查询
     */
    @RequestMapping("/q")
    public ModelAndView q(@RequestParam(value = "q",required = false) String q,
                          @RequestParam(value = "page",required = false) String page,
                          HttpServletRequest request) throws InvalidTokenOffsetsException, IOException, ParseException {
        ModelAndView mav = new ModelAndView();
        if (StringUtil.isEmpty(page)){
            page = "1";
        }
        mav.addObject("mainPage","foreground/blog/result.jsp");
        //在lucene中查询
        List<Blog> blogList = blogIndex.searchBlog(q.trim());
        //计算查询的范围
        int toIndex = 0;
        if (blogList.size()>=Integer.parseInt(page)*10){
            toIndex = Integer.parseInt(page)*10;
        }else {
            toIndex = blogList.size();
        }
        //添加翻页的数据到bloglist
        mav.addObject(Const.BLOG_LIST,blogList.subList((Integer.parseInt(page)-1)*10,toIndex));
        mav.addObject("pageCode",getUpAndDownPageCode(
                Integer.parseInt(page),blogList.size(),10,request.getServletContext().getContextPath(),
                q));
        mav.addObject("q",q);
        mav.addObject("resultTotal",blogList.size());
        mav.addObject("pageTitle","搜索关键字"+q+"结果页面_个人博客");
        mav.setViewName("index");
        return mav;
    }
    /**
     * 查询结果的翻页
     */
    private String getUpAndDownPageCode(int page,int totalNum,int pageSize,
                                        String projectContext,String q){
        StringBuffer pageCode = new StringBuffer();
        //总页数
        int totalPage = totalNum%pageSize==0?totalNum%pageSize:totalNum%pageSize+1;
        if (totalPage==0){
            return "";
        }
        pageCode.append("<nav>");
        pageCode.append("<ur class='pager'>");
            //如果当前页大于1
        if (page>1){
            pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+
                    (page-1)+"&q="+q+"'>上一页</a></li>");
        }else {
            //如果当前页小于1
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }
            //不是最后一页
        if (page<totalPage){
            pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+
                    (page+1)+"&q="+q+"'>下一页</a></li>");
        }else {
            //是最后一页
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }
        pageCode.append("</ur>");
        pageCode.append("</nav>");
        return pageCode.toString();
    }
}
