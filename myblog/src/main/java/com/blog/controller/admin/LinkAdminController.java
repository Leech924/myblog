package com.blog.controller.admin;

import com.blog.entity.Link;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.service.LinkService;
import com.blog.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 友情链接管理
 */
@Controller
@RequestMapping(value = "/admin/link")
public class LinkAdminController {
    @Autowired
    private LinkService linkService;
    /**友情链接列表*/
    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page",required = false)String page,
                       @RequestParam(value = "rows",required = false) String rows,
                       HttpServletResponse response) throws IOException {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String,Object> map = new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        /**查询友情链接列表*/
        List<Link> list = linkService.list(map);
        /**查询总共有多少条数据*/
        Long total = linkService.getTotal(map);
        /**将数据写入response*/
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);
        return null;
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(HttpServletResponse response,Link link) throws IOException {
        int resultTotal;
        if (link.getId()==null){
//            添加
        resultTotal = linkService.insertLink(link);
        }else {
//            修改
            resultTotal = linkService.updateLink(link);
        }
        JSONObject result = new JSONObject();
        if(resultTotal>0){
            result.put("success",Boolean.valueOf("true"));
        }else {
            result.put("success",Boolean.valueOf("false"));
        }
        ResponseUtil.write(response,result);
        return null;
    }
    //删除
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("ids") String ids,HttpServletResponse response) throws IOException {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();
        for (int i = 0;i<idsStr.length;i++){
            linkService.deleteLink(Integer.parseInt(idsStr[i]));
        }
        result.put("success",Boolean.valueOf("true"));
        ResponseUtil.write(response,result);
        return null;
    }
}
