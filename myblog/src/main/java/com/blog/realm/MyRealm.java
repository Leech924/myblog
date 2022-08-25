package com.blog.realm;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private BloggerService bloggerService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        将controller传过来的token强转为usernamepasswordtoken，获取用户名
        UsernamePasswordToken uptoken = (UsernamePasswordToken) authenticationToken;
        String username = uptoken.getUsername();
//        从数据库查询blogger对象
        Blogger blogger = bloggerService.getByUserName(username);
//        通过shiro验证登录
        if(blogger != null){
            SecurityUtils.getSubject().getSession().setAttribute("currentUser",blogger);
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, blogger.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}
