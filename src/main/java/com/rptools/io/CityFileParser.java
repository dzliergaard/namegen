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

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rptools.city.Cities;

/**
 * Parses the city file from S3, which contains information about how to generate names of inns and shoppes
 */
@Component
@CommonsLog
public class CityFileParser extends FileParser<Cities> {
    private final Gson gson;

    @Autowired
    public CityFileParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    protected Cities parseFileData(String data) {
        return gson.fromJson(data, Cities.class);
    }
}
