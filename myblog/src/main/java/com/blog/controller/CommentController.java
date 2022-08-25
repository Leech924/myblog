package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.utils.ResponseUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 前端用户提交评论
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @RequestMapping("/save")
    public String save(Comment comment, @RequestParam("imageCode") String imageCode,
                       HttpServletRequest request, HttpServletResponse response,
                       HttpSession session) throws IOException {
        String sRand = (String) session.getAttribute("sRand");
        JSONObject result = new JSONObject();
        Integer resultTotal = 0;
        if (!sRand.equals(imageCode)){
            result.put("seccess",Boolean.FALSE);
            result.put("errorInfo","验证码错误");
        }else {
            String userIp = request.getRemoteAddr();
            comment.setUserIp(userIp);
            if (comment.getId()==null){
                resultTotal = commentService.add(comment);
                //对应博客评论数加1
                Blog blog = blogService.queryForId(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit()+1);
                blogService.updateBlog(blog);
            }
        }
        if (resultTotal>0){
            result.put("success",Boolean.TRUE);
        }else {
            result.put("success", Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }
}
