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

package com.rptools.data;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.http.NoHttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive.Builder;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Get;
import com.google.api.services.drive.DriveRequest;
import com.rptools.auth.UserCredentials;

/**
 * Bean to create Google {@link com.google.api.services.drive.Drive} class using current user credential
 */
@CommonsLog
@Component
public class DriveService {
    private static final String APPLICATION_NAME = "DZLier RPTools";
    private static final String AUTH_REQ_EXC = "User must be authenticated to get/save content.";
    private static final String EXECUTION_EXC = "Exception attempting to execute DriveRequest %s on try %d";

    private final HttpTransport transport;
    private final JsonFactory jsonFactory;
    private final UserCredentials userCredentials;

    @Autowired
    public DriveService(HttpTransport transport, JsonFactory jsonFactory, UserCredentials userCredentials) {
        this.transport = transport;
        this.jsonFactory = jsonFactory;
        this.userCredentials = userCredentials;
    }

    public Files getDriveFiles() {
        Credential credential = userCredentials.getCredential().orElseThrow(() -> new RuntimeException(AUTH_REQ_EXC));
        return new Builder(transport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build().files();
    }

    public <T> T execute(DriveRequest<T> driveRequest) throws IOException {
        int tries = 3;
        while (tries > 0) {
            try {
                return driveRequest.execute();
            } catch (NoHttpResponseException e) {
                log.error(String.format(EXECUTION_EXC, driveRequest.getClass().getSimpleName(), 4 - tries), e);
                tries--;
            }
        }
        log.error("Failed 3 successive attempts to call " + driveRequest.getClass().getSimpleName());
        return null;
    }

    public InputStream executeMediaAsInputStream(Get driveRequest) throws IOException {
        int tries = 3;
        while (tries > 0) {
            try {
                return driveRequest.executeMediaAsInputStream();
            } catch (NoHttpResponseException e) {
                log.error(String.format(EXECUTION_EXC, driveRequest.getClass().getSimpleName(), 4 - tries), e);
                tries--;
            }
        }
        log.error("Failed 3 successive attempts to call " + driveRequest.getClass().getSimpleName());
        return null;
    }
}
