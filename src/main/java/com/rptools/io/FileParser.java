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

package com.rptools.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.extern.apachecommons.CommonsLog;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.rptools.io.FileUtils;

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

    private final FileUtils fileUtils;
    private final Gson gson;

    public FileParser(FileUtils fileUtils, Gson gson) {
        this.fileUtils = fileUtils;
        this.gson = gson;
    }

    /**
     * First looks for the file locally if it has already been downloaded from a previous activation.
     * If not, will save the file locally so it can use it next time.
     */
    public T parseFile(String fileName, Class<T> tClass) {
        Path filePath = fileUtils.localFilePath(fileName + ".json");
        try {
            String content = JOINER.join(Files.readAllLines(filePath, UTF8_CHARSET));
            return gson.fromJson(content, tClass);
        } catch (IOException e) {
            log.error(String.format("Error parsing local JSON file %s, getting from S3", filePath.getFileName()), e);
            return null;
        }
    }

    protected abstract T parseFileData(String data);
}
