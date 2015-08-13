package com.rptools.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Aspect
@Component
public class RequestParamAdvisor {
    private Provider<User> userProvider;

    @Autowired
    public RequestParamAdvisor(Provider<User> userProvider) {
        this.userProvider = userProvider;
    }

    @Before("execution(com.rptools.server.* *(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void preHandle() throws Exception {
        UserService service = UserServiceFactory.getUserService();
        if (!service.isUserLoggedIn()) {
            userProvider.empty();
            return;
        }
        userProvider.set(UserServiceFactory.getUserService().getCurrentUser());
    }
}
