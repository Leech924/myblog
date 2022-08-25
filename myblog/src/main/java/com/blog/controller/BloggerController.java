package com.blog.controller;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.service.impl.BloggerServiceImpl;
import com.blog.utils.CryptographyUtil;
import com.blog.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/blogger")
public class BloggerController {
    @Autowired
    private BloggerService bloggerService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Blogger blogger, HttpServletRequest request){
        String username = blogger.getUserName();
        String password = blogger.getPassword();
        //使用md5加密，username为salt
        String md5password = CryptographyUtil.md5(password, username);
        Subject subject = SecurityUtils.getSubject();
        //创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, md5password);
        try {
            //调用login将token传到realm的方法中进行登录
            subject.login(token);
            return "redirect:/admin/main.jsp";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("username",username);
            request.setAttribute("errorInfo","用户名或密码错误！");
        }
        return "login";
    }

    /**
     * 关于博主
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe(){
        ModelAndView mav = new ModelAndView();
        Blogger blogger = bloggerService.find();
        mav.addObject("blogger",blogger);
        mav.addObject("mainPage","foreground/blogger/info.jsp");
        mav.addObject("pageTitle","关于博主_个人博客系统");
        mav.setViewName("index");
        return mav;
    }

}
