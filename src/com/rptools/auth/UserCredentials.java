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

import java.io.IOException;
import java.util.Optional;

import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 * Session-scoped credentials that tie the user to their Google Drive account in
 * order to store app data there.
 */
@CommonsLog
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserCredentials {
    private GoogleCredential.Builder builder;
    @Setter private GoogleCredential credential;

    @Autowired
    public UserCredentials(
            ApacheHttpTransport transport,
            FileDataStoreFactory fileDataStoreFactory,
            GoogleAuthorizationCodeFlow googleFlow,
            GoogleClientSecrets clientSecrets,
            JsonFactory jsonFactory,
            String clientId) throws IOException {
        this.builder = new GoogleCredential.Builder()
            .setClientSecrets(clientId, clientSecrets.getWeb().getClientSecret())
            .setJsonFactory(jsonFactory)
            .setTransport(transport)
            .addRefreshListener(new DataStoreCredentialRefreshListener("me", fileDataStoreFactory));
        Credential credential = googleFlow.loadCredential("me");
        Optional.ofNullable(credential).ifPresent(this::setFromCredential);
    }

    public Optional<GoogleCredential> getCredential() {
        return Optional
            .ofNullable(credential)
            .filter(cred -> new DateTime(cred.getExpirationTimeMilliseconds()).isAfterNow());
    }

    public void setFromCredential(Credential credential) {
        this.credential = builder.setTokenServerEncodedUrl(credential.getTokenServerEncodedUrl()).build();
        this.credential.setAccessToken(credential.getAccessToken());
        this.credential.setRefreshToken(credential.getRefreshToken());
        this.credential.setExpirationTimeMilliseconds(credential.getExpirationTimeMilliseconds());
    }
}
