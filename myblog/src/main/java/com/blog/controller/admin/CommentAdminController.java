package com.blog.controller.admin;

/**
 * 评论管理
 */

import com.blog.entity.Comment;
import com.blog.entity.PageBean;
import com.blog.service.CommentService;
import com.blog.utils.DateJsonValueProcessor;
import com.blog.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/comment")
public class CommentAdminController {
    @Autowired
    private CommentService commentService;
    /**
     * 查询评论列表
     */
    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page",required = false) String page,
                       @RequestParam(value = "rows",required = false) String rows,
                       @RequestParam(value = "state",required = false) String state,
                       HttpServletResponse response) throws IOException {
        //加载翻页,状态等参数
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        HashMap<String, Object> map = new HashMap<>();
        map.put("size",pageBean.getPageSize());
        map.put("start",pageBean.getStart());
        map.put("state",state);
        //查询评论列表
        List<Comment> list = commentService.list(map);
        //查询评论数
        Long total = commentService.getTotal(map);
        JSONObject result = new JSONObject();
        JsonConfig config = new JsonConfig();
        //date日期格式将按照yyyy-MM-dd
        config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
        //将list集合转换成json数组并按照jsonconfig的格式
        JSONArray jsonArray = JSONArray.fromObject(list, config);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);
        return null;
    }
    //删除
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam(value = "ids",required = false) String ids,HttpServletResponse response) throws IOException {
        String[] strs= ids.split(",");
            for (String str:strs){
                int id = Integer.parseInt(str);
                commentService.delete(id);
            }
        JSONObject result = new JSONObject();
        result.put("success",Boolean.TRUE);
        ResponseUtil.write(response,result);
        return null;
    }
    //审核评论
    @RequestMapping(value = "/review")
    public String review(@RequestParam(value = "ids",required = false) String ids,
                         @RequestParam(value = "state",required = false) String state,
                         HttpServletResponse response) throws IOException {
        String[] strs = ids.split(",");
        for(String str:strs){
            Comment comment = new Comment();
            comment.setId(Integer.parseInt(str));
            comment.setState(Integer.parseInt(state));
            commentService.update(comment);
        }
        JSONObject result = new JSONObject();
        result.put("success",Boolean.TRUE);
        ResponseUtil.write(response,result);
        return null;
    }
}
