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

package com.rptools.name;

import java.io.IOException;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.gson.stream.JsonReader;

/**
 * Represents a generated Name entity
 */
@Data
@CommonsLog
public class Name {
    private String id;
    private String text;

    public Name() {
    }

    public Name(String text) {
        this(null, text);
    }

    public Name(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Name(JsonReader jsonReader) throws IOException {
        this.id = jsonReader.nextName();
        this.text = jsonReader.nextString();
    }

    /**
     * A soft equals that only checks for ID equivalence, to match {@code Collection.contains()} calls
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Name)) {
            return false;
        }
        Name that = (Name) o;
        return (this.id == null && that.id == null) || (this.id != null && this.id.equals(that.id));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getId()).build();
    }
}
