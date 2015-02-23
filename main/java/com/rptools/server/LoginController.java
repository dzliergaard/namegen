package com.rptools.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.base.Strings;
import com.rptools.util.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DZL on 2/15/14.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired private Provider<User> userProvider;

    @RequestMapping(method= RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("Login");
        String redirectUrl = request.getParameter("redirect");
        if (Strings.isNullOrEmpty(redirectUrl)) {
            redirectUrl = "/";
        }
        RedirectView rv;
        if (userProvider.has()) {
            rv = new RedirectView(UserServiceFactory.getUserService().createLoginURL(request.getRequestURI()));
        } else {
            rv = new RedirectView(redirectUrl);
        }
        mav.setView(rv);
        return mav;
    }
}
