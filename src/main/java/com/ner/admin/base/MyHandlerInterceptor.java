package com.ner.admin.base;

import com.ner.admin.entity.User;
import com.ner.admin.service.SiteService;
import com.ner.admin.service.UserService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jll on 2017/11/30.
 * todo:
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private SiteService siteService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if (siteService == null || userService == null) {//解决service为null无法注入问题
            System.out.println("siteService is null!!!");
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            siteService = (SiteService) factory.getBean("siteService");
            userService = (UserService) factory.getBean("userService");

        }
        httpServletRequest.setAttribute("site",siteService.getCurrentSite());
        User user = userService.findUserById(MySysUser.id());
        if(user != null){
            httpServletRequest.setAttribute("currentUser",user);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
