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

package com.rptools.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.stereotype.Component;

/**
 * Helpful file read/write utilities
 */
@CommonsLog
@Component
public class FileUtils {
    private final Path baseDir;

    public FileUtils() throws IOException {
        this.baseDir = Paths.get("data");
        if (!Files.exists(baseDir) || !Files.isDirectory(baseDir)) {
            Files.deleteIfExists(baseDir);
            Files.createDirectory(baseDir);
        }
    }

    public Optional<Path> getLocalContentFile(String fileName) {
        return Optional.of(Paths.get(baseDir.toString(), fileName + ".json")).filter(path -> Files.exists(path));
    }

    public void writeFile(String fileName, String content) {
        try {
            Path filePath = Paths.get(baseDir.toString(), fileName + ".json");
            Path localFilePath = Files.createFile(filePath);
            File localFile = localFilePath.toFile();
            com.google.common.io.Files.write(content, localFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Exception creating local file.", e);
            throw new RuntimeException("Exception creating local file.", e);
        }
    }
}
