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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.rptools.city.City;
import com.rptools.name.Name;

/**
 * Session-scoped component that represents the content the application has stored to the user's Google Drive
 * appdata folder
 */
@Component
@CommonsLog
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSavedContent {
    private final UserDrive userDrive;
    private final Gson gson;

    @Getter @Setter private Collection<Name> names = Sets.newHashSet();
    @Getter @Setter private Collection<City> cities = Sets.newHashSet();

    @Autowired
    public UserSavedContent(UserDrive userDrive, Gson gson) {
        this.userDrive = userDrive;
        this.gson = gson;
        refreshContent();
    }

    public void refreshContent() {
        InputStream is = userDrive.downloadAppDataFile();
        if (is == null) {
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line, response = "";
        try {
            while ((line = br.readLine()) != null) {
                response += line;
            }
            br.close();
            parseContentFile(response);
        } catch (IOException e) {
            log.error("Exception reading file content", e);
            throw new RuntimeException("Exception reading file content", e);
        }
    }

    private void parseContentFile(String driveData) {
        UserSavedContent content = gson.fromJson(driveData, UserSavedContent.class);
        names = content.getNames();
        cities = content.getCities();
    }

    public Name addName(@NonNull Name name) {
        name.setText(WordUtils.capitalize(name.getText().toLowerCase()));
        if (Strings.isNullOrEmpty(name.getId())) {
            name.setId(UUID.randomUUID().toString());
        }
        if (names.contains(name)) {
            Name oldName = names.stream().filter(name1 -> name1.equals(name)).findFirst().orElse(null);
            if (name.getText().equals(oldName.getText())) {
                // id and text match, no need to update
                return name;
            }
            names.remove(oldName);
        }
        names.add(name);
        userDrive.writeContentFile();

        return name;
    }

    public boolean deleteName(Name name) {
        boolean changed = names.remove(name);
        userDrive.writeContentFile();
        return changed;
    }

    public boolean clearNames() {
        if (names.size() > 0) {
            names.clear();
            userDrive.writeContentFile();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public Map<String, UserSavedContent> asDataMap() {
        return ImmutableMap.of("data", this);
    }
}
