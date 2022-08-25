package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.blog.lucene.BlogIndex;
import com.blog.mappers.BlogMapper;
import com.blog.service.BlogService;
import com.blog.utils.DateJsonValueProcessor;
import com.blog.utils.ResponseUtil;
import com.blog.utils.StringUtil;
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
@RequestMapping(value = "/admin/blog")
public class BlogAdminController {
    @Autowired
    private BlogService blogService;
    private BlogIndex blogIndex = new BlogIndex();

    //写博客
    @RequestMapping(value = "/save")
    public String save(Blog blog, HttpServletResponse response) throws IOException {
        Integer resultTotal;
        if(blog.getId()==null){
            /**添加一条博客*/
            resultTotal = blogService.insertBlog(blog);
            blogIndex.addIndex(blog);
        }else {
            /**修改一条博客*/
            resultTotal = blogService.updateBlog(blog);
            blogIndex.updateIndex(blog);
        }
        JSONObject result = new JSONObject();
        if (resultTotal>0){
            result.put("success",Boolean.valueOf(true));
        }else {
            result.put("success",Boolean.valueOf(false));
        }
        ResponseUtil.write(response,result);
        return null;
    }
    /**查看博客列表*/
    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page",required = false) String page,
                       @RequestParam(value = "rows",required = false) String rows,
                       HttpServletResponse response,
                       Blog blog) throws IOException {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        HashMap<String, Object> map = new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        map.put("title", StringUtil.formatLike(blog.getTitle()));
        //分页查询博客信息列表
        List<Blog> list = blogService.list(map);
        //查询总共有多少条的博客信息
        Long total = blogService.getTotal(map);
        JSONObject result = new JSONObject();
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray = JSONArray.fromObject(list,config);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);
        return null;
    }
    @RequestMapping("{queryForId}")
    public String queryForId(@RequestParam("id") String id,HttpServletResponse response) throws IOException {
        Blog blog = blogService.queryForId(Integer.parseInt(id));
        JSONObject result = JSONObject.fromObject(blog);
        ResponseUtil.write(response,result);
        return null;
    }
    @RequestMapping("delete")
    public String delete(@RequestParam("ids") String ids,HttpServletResponse response) throws IOException {
        String[] strs = ids.split(",");
        for (String id:strs){
            blogService.deleteBlog(Integer.parseInt(id));
            blogIndex.deleteIndex(id);
        }
        JSONObject result = new JSONObject();
        result.put("success",Boolean.valueOf("true"));
        ResponseUtil.write(response,result);
        return null;
    }
}
