package com.mysiteforme.admin.base;

import com.mysiteforme.admin.entity.User;
import com.mysiteforme.admin.realm.AuthRealm.ShiroUser;
import com.mysiteforme.admin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    public User getCurrentUser() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser == null) {
            return null;
        }
        User loginUser = userService.selectById(shiroUser.getId());
        return loginUser;
    }
}
