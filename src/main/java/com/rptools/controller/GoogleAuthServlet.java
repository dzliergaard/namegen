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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.gson.Gson;
import com.rptools.auth.GoogleAuth;

/**
 * REST controller that handles user authentication-related requests
 */
@CommonsLog
@RestController
@RequestMapping("/googleAuth")
public class GoogleAuthServlet {
    private final GoogleAuth googleAuth;
    private final Gson gson;

    @Autowired
    public GoogleAuthServlet(GoogleAuth googleAuth, Gson gson) {
        this.googleAuth = googleAuth;
        this.gson = gson;
    }

    /**
     * Redirects request to google's OAuth2 authorization request page for this application.
     * The page for RPTools requests access for keeping app data in the user's Google Drive.
     */
    @RequestMapping(params = "!code")
    public void getAuthorizationCode(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "state", required = false) String state) throws URISyntaxException, IOException {
        response.sendRedirect(
            new URIBuilder(googleAuth.newAuthorizationUrl().build())
                .setParameters(googleAuth.createAuthorizationCodeRequestParameters(request, state))
                .build()
                .toString());
    }

    /**
     * Handles the response from Google's OAuth2 authorization link, and forwards the response back to its origin, or
     * /name by default
     */
    @RequestMapping(params = { "code", "!error" })
    public void googleAuth(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("code") String authorizationCode,
            @RequestParam("state") String state) throws IOException, URISyntaxException {
        refreshAccessCode(request, authorizationCode);
        Map<String, List<String>> parameters = gson.fromJson(state, new HashMap<String, String[]>().getClass());
        String returnUri = Optional.ofNullable(parameters.get("returnUri")).map(li -> li.get(0)).orElse("/name");
        response.sendRedirect(returnUri);
    }

    private void refreshAccessCode(HttpServletRequest request, String authorizationCode)
            throws URISyntaxException, IOException {
        int tries = 3;
        while (tries > 0) {
            try {
                TokenResponse tokenResponse = googleAuth.getTokenResponse(request, authorizationCode);
                googleAuth.createAndStoreCredential(tokenResponse);
                return;
            } catch (NoHttpResponseException e) {
                log.error("No HTTP response from accounts.google.com on try " + Integer.toString(4 - tries), e);
                tries--;
            }
        }
    }

    /**
     * Signs the user out of their Google account.
     */
    @RequestMapping("signOut")
    public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        googleAuth.deleteCredential();
        response.sendRedirect(Optional.ofNullable((String) request.getAttribute("returnUri")).orElse("/"));
    }

    /**
     * Simply confirms whether the user is signed in.
     */
    @RequestMapping("isSignedIn")
    public boolean isSignedIn() {
        return googleAuth.getCredential().isPresent();
    }
}
