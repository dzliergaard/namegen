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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gson.Gson;
import com.rptools.auth.UserCredentials;

/**
 * REST controller that handles user authentication-related requests
 */
@CommonsLog
@RestController
@RequestMapping("/googleAuth")
public class GoogleAuthServlet {
    private final GoogleAuthorizationCodeFlow googleFlow;
    private final Gson gson;
    private final UserCredentials userCredentials;
    private final FileDataStoreFactory fileDataStoreFactory;
    private final String googleClientId;
    private final String googleScopes;

    @Autowired
    public GoogleAuthServlet(
            GoogleAuthorizationCodeFlow googleFlow,
            Gson gson,
            UserCredentials userCredentials,
            FileDataStoreFactory fileDataStoreFactory,
            @Value("${com.rptools.googleClientId}") String googleClientId,
            @Value("${com.rptools.googleScopes}") String googleScopes) {
        this.googleFlow = googleFlow;
        this.gson = gson;
        this.userCredentials = userCredentials;
        this.fileDataStoreFactory = fileDataStoreFactory;
        this.googleClientId = googleClientId;
        this.googleScopes = googleScopes;
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
        GoogleAuthorizationCodeRequestUrl requestUrl = googleFlow.newAuthorizationUrl();
        response.sendRedirect(
            new URIBuilder(requestUrl.build())
                .setParameters(createAuthorizationCodeRequestParameters(request, state))
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

    private List<NameValuePair> createAuthorizationCodeRequestParameters(HttpServletRequest request, String state) {
        GenericUrl url = new GenericUrl(request.getRequestURL().toString());
        url.setRawPath("/googleAuth");
        return Arrays.asList(
            new BasicNameValuePair("scope", googleScopes),
            new BasicNameValuePair("state", state),
            new BasicNameValuePair("redirect_uri", url.build()),
            new BasicNameValuePair("response_type", "code"),
            new BasicNameValuePair("client_id", googleClientId));
    }

    private TokenResponse refreshAccessCode(HttpServletRequest request, String authorizationCode)
            throws URISyntaxException, IOException {
        int tries = 3;
        boolean success = false;
        TokenResponse tokenResponse = null;
        while (!success && tries > 0) {
            try {
                tokenResponse = getTokenResponse(request, authorizationCode);
                success = true;
            } catch (NoHttpResponseException e) {
                log.error("No HTTP response from accounts.google.com on try " + Integer.toString(4 - tries), e);
                tries--;
            }
        }
        if (!success) {
            return null;
        }
        googleFlow.createAndStoreCredential(tokenResponse, "me");
        GoogleCredential credential = new GoogleCredential.Builder()
            .addRefreshListener(new DataStoreCredentialRefreshListener("me", fileDataStoreFactory))
            .build();
        credential.setFromTokenResponse(tokenResponse);
        userCredentials.setFromCredential(credential);

        return tokenResponse;
    }

    private TokenResponse getTokenResponse(HttpServletRequest request, String authorizationCode) throws IOException {
        GoogleAuthorizationCodeTokenRequest tokenRequest =
                googleFlow.newTokenRequest(authorizationCode).setGrantType("authorization_code").setRedirectUri(
                    request.getRequestURL().toString());

        return tokenRequest.execute();
    }

    /**
     * Signs the user out of their Google account.
     */
    @RequestMapping("clear")
    public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userCredentials.setCredential(null);
        googleFlow.getCredentialDataStore().delete("me");
        response.sendRedirect(Optional.ofNullable((String) request.getAttribute("returnUri")).orElse("/"));
    }

    /**
     * Simply confirms whether the user is signed in.
     */
    @RequestMapping("isSignedIn")
    public boolean isSignedIn() {
        return userCredentials.getCredential().isPresent();
    }
}
