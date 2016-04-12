package com.rptools.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rptools.annotation.RequiresGoogleAuth;
import com.rptools.data.UserSavedContent;

@RestController
@RequestMapping(value = { "/" })
public class HomeController {
    private final UserSavedContent userSavedContent;

    @Autowired
    public HomeController(UserSavedContent userSavedContent) {
        this.userSavedContent = userSavedContent;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletResponse response) throws IOException {
        response.sendRedirect("name");
    }

    @RequiresGoogleAuth
    @RequestMapping(value = "/userSavedContent", method = RequestMethod.GET)
    public Map<String, UserSavedContent> getUserContent(HttpServletRequest request, HttpServletResponse response) {
        return userSavedContent.asDataMap();
    }
}
