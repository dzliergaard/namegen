package com.rptools.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestParamInterceptor extends HandlerInterceptorAdapter{
    @Autowired private Provider<User> userProvider;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        UserService service = UserServiceFactory.getUserService();
        if (!service.isUserLoggedIn()) {
            userProvider.empty();
            return true;
        }
        userProvider.set(UserServiceFactory.getUserService().getCurrentUser());
        return true;
    }
}