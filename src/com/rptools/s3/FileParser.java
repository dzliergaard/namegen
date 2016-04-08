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
import java.util.Optional;

import lombok.extern.apachecommons.CommonsLog;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

/**
 * Abstract class that parses a file from S3 into a tangible/useful object
 * 
 * @param <T>
 *            The type of data the file is parsed into
 */
@CommonsLog
public abstract class FileParser<T> {
    private final AmazonS3 s3;
    private final String bucketName;

    public FileParser(AmazonS3 s3, String bucketName) {
        this.s3 = s3;
        this.bucketName = bucketName;
    }

    public T parseFile(String fileName) {
        GetObjectRequest gor = new GetObjectRequest(bucketName, fileName);
        S3Object file = s3.getObject(gor);
        return Optional
            .ofNullable(file)
            .map(S3Object::getObjectContent)
            .map(this::parseStream)
            .map(this::parseFileData)
            .orElse(null);
    }

    private String parseStream(S3ObjectInputStream stream) {
        try {
            return IOUtils.toString(stream).trim();
        } catch (IOException e) {
            log.warn("Error parsing s3 file data", e);
            return null;
        }
    }

    protected abstract T parseFileData(String data);
}
