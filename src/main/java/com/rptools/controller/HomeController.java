/*
 * RPToolkit - Tools to assist Role-Playing Game masters and players
 * Copyright (C) 2016 Dane Zeke Liergaard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Rest controller that directs requests to base URL and provides {@link UserSavedContent} to /userSavedContent
 */
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
