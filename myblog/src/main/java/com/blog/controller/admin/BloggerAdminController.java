package com.blog.controller.admin;
/**
 * 个人信息管理
 */

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.utils.CryptographyUtil;
import com.blog.utils.DateUtil;
import com.blog.utils.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/admin/blogger")
public class BloggerAdminController {
    @Autowired
    private BloggerService bloggerService;
@RequestMapping(value = "/save")
    public String save(@RequestParam("imageFile") MultipartFile imageFile, Blogger blogger,
                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!imageFile.isEmpty()){
            String filePath = request.getServletContext().getRealPath("/");
            String imageName = DateUtil.getCurrentDateStr() + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
            blogger.setImgName(imageName);
        }
        Integer total = bloggerService.updateUser(blogger);
        StringBuffer result = new StringBuffer();
        if (total>0){
        result.append("<script lauguage='javascript'>alert('修改成功');</script>");
        }else {
            result.append("<script lauguage='javascript'>alert('修改失败');</script>");
        }
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 获取博主的json格式
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/find")
    public String find(HttpServletResponse response) throws IOException {
        Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        JSONObject result = JSONObject.fromObject(blogger);
        ResponseUtil.write(response,result);
        return null;

    }
    /**
     * 修改密码
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(@RequestParam(value = "newPassword")String newPassword,
                                 @RequestParam(value = "id")String id,
                                 HttpServletResponse response) throws IOException {
        Blogger blogger = new Blogger();
        blogger.setId(Integer.parseInt(id));
        //获取username使用md5加密密码
        Blogger blogger2 = (Blogger)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        blogger.setPassword(CryptographyUtil.md5(newPassword,blogger2.getUserName()));
        Integer resultTotal = bloggerService.updateUser(blogger);
        org.json.JSONObject result = new org.json.JSONObject();
        if (resultTotal>0){
            result.put("success",Boolean.TRUE);
        }else {
            result.put("success",Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 安全退出
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}
