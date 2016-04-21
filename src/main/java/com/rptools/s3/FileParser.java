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

package com.rptools.s3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.extern.apachecommons.CommonsLog;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.rptools.util.FileUtils;

/**
 * Abstract class that parses a file from S3 into a tangible/useful object
 *
 * Children are responsible for writing implementation of parsing the .txt file in S3.
 *
 * When {@code parseFile ()} is called, it first checks if a local json file exists for the file name.
 * If the local file exists and is more recent than the s3 file, it parses it and returns.
 * Otherwise, it gets the file from S3, parses it to an object, and saves it in JSON form to a local file to be read
 * next time.
 *
 * @param <T>
 *            The type of data the file is parsed into
 */
@CommonsLog
public abstract class FileParser<T> {
    private static final Joiner JOINER = Joiner.on("");
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private final AmazonS3 s3;
    private final String bucketName;
    private final FileUtils fileUtils;
    private final Gson gson;

    public FileParser(AmazonS3 s3, String bucketName, @Qualifier("appContent") FileUtils fileUtils, Gson gson) {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.fileUtils = fileUtils;
        this.gson = gson;
    }

    /**
     * First looks for the file locally if it has already been downloaded from a previous activation.
     * If not, will save the file locally so it can use it next time.
     */
    public T parseFile(String fileName, Class<T> tClass) {
        // if local file does not exist, load from S3
        Optional<Path> localFilePath = fileUtils.getLocalContentFile(fileName);

        return localFilePath
            .filter(path -> !localFileOutOfDate(path, getS3ObjectMetadata(fileName)))
            .map(path -> parseLocalJsonFile(path, tClass))
            .orElseGet(() -> this.getFileFromS3(fileName));
    }

    private boolean localFileOutOfDate(Path localFilePath, ObjectMetadata s3Metadata) {
        try {
            DateTime localModified = new DateTime(Files.getLastModifiedTime(localFilePath).toMillis());
            DateTime s3Modified = new DateTime(s3Metadata.getLastModified());
            return localModified.isBefore(s3Modified);
        } catch (IOException e) {
            log.error("Exception accessing local file last modified time", e);
            return true;
        }
    }

    private T parseLocalJsonFile(Path filePath, Class<T> tClass) {
        try {
            String content = JOINER.join(Files.readAllLines(filePath, UTF8_CHARSET));
            return gson.fromJson(content, tClass);
        } catch (IOException e) {
            log.error(String.format("Error parsing local JSON file %s, getting from S3", filePath.getFileName()), e);
            return null;
        }
    }

    private ObjectMetadata getS3ObjectMetadata(String fileName) {
        return s3.getObjectMetadata(bucketName, fileName + ".txt");
    }

    private T getFileFromS3(String fileName) {
        GetObjectRequest gor = new GetObjectRequest(bucketName, fileName + ".txt");
        Optional<S3Object> file = Optional.ofNullable(s3.getObject(gor));
        Optional<T> result = file.map(S3Object::getObjectContent).map(str -> parseStream(str, fileName));
        return result.orElse(null);
    }

    private T parseStream(S3ObjectInputStream stream, String fileName) {
        try {
            T object = parseFileData(IOUtils.toString(stream).trim());
            fileUtils.writeFile(fileName, gson.toJson(object));
            return object;
        } catch (IOException e) {
            log.warn("Error parsing s3 file data", e);
            return null;
        }
    }

    protected abstract T parseFileData(String data);
}
