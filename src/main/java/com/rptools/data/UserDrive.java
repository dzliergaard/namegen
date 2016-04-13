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
import java.util.Collections;
import java.util.Optional;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

/**
 * Helper component between the {@link DriveService} and other components that want a sensible API for interacting
 * with the user's saved content
 */
@CommonsLog
@Component
public class UserDrive {
    private static final String FIELDS = "files(id, name)";
    private static final String APP_DATA = "appDataFolder";
    private final DriveService driveService;
    private final UserSavedContent userSavedContent;

    @Autowired
    public UserDrive(DriveService driveService, UserSavedContent userSavedContent) {
        this.driveService = driveService;
        this.userSavedContent = userSavedContent;
    }

    InputStream downloadAppDataFile() {
        return getAppDataFile().map(this::downloadFile).orElse(null);
    }

    private Optional<File> getAppDataFile() {
        try {
            List driveRequest = driveService.getDriveFiles().list().setSpaces(APP_DATA).setFields(FIELDS);
            FileList files = driveService.execute(driveRequest);
            return Optional.ofNullable(files.getFiles()).filter(li -> !li.isEmpty()).map(li -> li.get(0));
        } catch (IOException e) {
            log.error("Exception getting app data file.", e);
            throw new RuntimeException("Exception getting app data file.", e);
        }
    }

    File writeContentFile() {
        try {
            InputStream contentStream = IOUtils.toInputStream(userSavedContent.toString(), "UTF-8");
            InputStreamContent content = new InputStreamContent("application/json", contentStream);
            File fileMetadata = new File();
            fileMetadata.setName("content.json");
            fileMetadata.setParents(Collections.singletonList("appDataFolder"));
            return getAppDataFile()
                .map(File::getId)
                .map(file -> updateContentFile(file, fileMetadata, content))
                .orElseGet(() -> createContentFile(fileMetadata, content));
        } catch (IOException e) {
            log.error("Exception creating input stream of user saved content");
        }
        return null;
    }

    private InputStream downloadFile(File file) {
        try {
            log.info("Downloading appData file " + file.getName());
            return driveService.executeMediaAsInputStream(driveService.getDriveFiles().get(file.getId()));
        } catch (GoogleJsonResponseException e) {
            log.error("GoogleJsonResponseException downloading file.", e);
            return null;
        } catch (IOException e) {
            log.error("Exception downloading file.", e);
            throw new RuntimeException("Exception downloading file.", e);
        }
    }

    /**
     * A bug in google-api-java-client-1.21.0 incorrectly throws an IllegalArgumentException
     * when the method of a call is PATCH. 1.22.0 Has the issue fixed but is not available yet.
     * In the meantime we must first delete a file and then recreate it.
     */
    private File updateContentFile(String fileId, File file, InputStreamContent content) {
        try {
            deleteAppDataFile(fileId);
            log.info("Old content file deleted, uploading new file.");
            return driveService.execute(driveService.getDriveFiles().create(file, content).setFields("id"));
        } catch (IOException e) {
            log.error("Exception updating drive file.", e);
            throw new RuntimeException("Exception updating drive file.", e);
        }
    }

    private void deleteAppDataFile(String fileId) throws IOException {
        try {
            driveService.execute(driveService.getDriveFiles().delete(fileId));
        } catch (GoogleJsonResponseException e) {
            if (!"^File not found: .*".matches(e.getMessage())) {
                throw new RuntimeException("Exception deleting old app data file", e);
            }
        }
    }

    private File createContentFile(File file, InputStreamContent content) {
        try {
            log.info("Creating content app data file " + file.getName());
            return driveService.execute(driveService.getDriveFiles().create(file, content).setFields("id"));
        } catch (IOException e) {
            log.error("Exception creating drive file.", e);
            throw new RuntimeException("Exception creating drive file.", e);
        }
    }
}
