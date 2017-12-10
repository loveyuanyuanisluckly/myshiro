package com.uuzz.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zj
 * @desc 登陆过滤器<br>
 * 当用户已经登陆再次访问登陆页面
 * 或者注册页面时重定向至欢迎页面
 * @date 2017/12/8
 * @since 1.7
 */
public class LoginFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {

        HttpServletRequest req = (HttpServletRequest)request;
        if(WebUtils.getRequestUri(req).equals(req.getContextPath()+"/")){
            return false;
        }
        return !SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletResponse rep = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        String targetUrl = req.getContextPath()+Constants.HOME_PAGE_URL;
        rep.sendRedirect(rep.encodeRedirectURL(targetUrl));

        return Boolean.FALSE;
    }
}
