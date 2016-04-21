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

package com.rptools.auth;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.common.base.Splitter;

/**
 * Tracks user session scoped {@link GoogleCredential} and provides helper functions for authentication
 */
@CommonsLog
@Component
@Scope(value = SCOPE_SESSION, proxyMode = TARGET_CLASS)
public class GoogleAuth implements Serializable {
    private static final Splitter SPLITTER = Splitter.on(" ");
    private final GoogleAuthorizationCodeFlow googleFlow;
    private final GoogleCredential.Builder credentialBuilder;
    private final HttpSession httpSession;
    private final String googleClientId;
    private final String googleScopes;

    @Setter private GoogleCredential credential;

    @Autowired
    public GoogleAuth(
            DataStore<StoredCredential> dataStore,
            HttpTransport transport,
            GoogleClientSecrets clientSecrets,
            JsonFactory jsonFactory,
            HttpSession httpSession,
            @Value("${com.rptools.googleClientId}") String googleClientId,
            @Value("${com.rptools.googleScopes}") String googleScopes) throws IOException {
        this.httpSession = httpSession;
        googleFlow = new Builder(transport, jsonFactory, clientSecrets, parseScopes(googleScopes))
            .setAccessType("offline")
            .setCredentialDataStore(dataStore)
            .build();
        credentialBuilder = new GoogleCredential.Builder()
            .setClientSecrets(googleClientId, clientSecrets.getWeb().getClientSecret())
            .setJsonFactory(jsonFactory)
            .setTransport(transport)
            .addRefreshListener(new DataStoreCredentialRefreshListener("me", dataStore));

        credentialBuilder.setTokenServerEncodedUrl(googleFlow.getTokenServerEncodedUrl());
        this.googleClientId = googleClientId;
        this.googleScopes = googleScopes;
        buildGoogleCredential();
    }

    private void buildGoogleCredential() throws IOException {
        Optional.ofNullable(loadCredential()).map(cred -> credentialBuilder.build()).ifPresent(this::setCredential);
    }

    private Credential loadCredential() throws IOException {
        return googleFlow.loadCredential("me");
    }

    public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
        return googleFlow.newAuthorizationUrl();
    }

    public void createAndStoreCredential(TokenResponse tokenResponse) throws IOException {
        googleFlow.createAndStoreCredential(tokenResponse, "me");
        credential = credentialBuilder.build().setFromTokenResponse(tokenResponse);
    }

    public TokenResponse getTokenResponse(HttpServletRequest request, String authorizationCode) throws IOException {
        GoogleAuthorizationCodeTokenRequest tokenRequest =
                googleFlow.newTokenRequest(authorizationCode).setGrantType("authorization_code").setRedirectUri(
                    request.getRequestURL().toString());

        return tokenRequest.execute();
    }

    public Optional<GoogleCredential> getCredential() {
        return Optional
            .ofNullable(credential)
            .filter(cred -> new DateTime(cred.getExpirationTimeMilliseconds()).isAfterNow());
    }

    public void deleteCredential() throws IOException {
        this.googleFlow.getCredentialDataStore().delete("me");
        httpSession.invalidate();
    }

    public List<NameValuePair> createAuthorizationCodeRequestParameters(HttpServletRequest request, String state) {
        GenericUrl url = new GenericUrl(request.getRequestURL().toString());
        url.setRawPath("/googleAuth");
        return Arrays.asList(
            new BasicNameValuePair("state", state),
            new BasicNameValuePair("redirect_uri", url.build()),
            new BasicNameValuePair("response_type", "code"),
            new BasicNameValuePair("scope", googleScopes),
            new BasicNameValuePair("client_id", googleClientId));
    }

    private static Collection<String> parseScopes(String googleScopes) {
        return SPLITTER.splitToList(googleScopes);
    }
}
