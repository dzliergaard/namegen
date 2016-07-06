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

import com.google.common.base.Joiner;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract class that parses a file from S3 into a tangible/useful object
 * <p>
 * Children are responsible for writing implementation of parsing the .txt file in S3.
 * <p>
 * When {@code parseFile ()} is called, it first checks if a local json file exists for the file name.
 * If the local file exists and is more recent than the s3 file, it parses it and returns.
 * Otherwise, it gets the file from S3, parses it to an object, and saves it in JSON form to a local file to be read
 * next time.
 *
 * @param <T> The type of data the file is parsed into
 */
@CommonsLog
public abstract class FileParser<T> {
    private static final Joiner JOINER = Joiner.on("");

    private final FileUtils fileUtils;

    public FileParser(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    /**
     * First looks for the file locally if it has already been downloaded from a previous activation.
     * If not, will save the file locally so it can use it next time.
     */
    public T parseFile(String fileName, Class<T> tClass) {
        Path filePath = fileUtils.localFilePath(fileName);
        try {
            return parseFileData(JOINER.join(Files.readAllLines(filePath)));
        } catch (IOException e) {
            log.error(String.format("Error parsing local JSON file %s, getting from S3", filePath.getFileName()), e);
            return null;
        }
    }

    protected abstract T parseFileData(String data);
}
