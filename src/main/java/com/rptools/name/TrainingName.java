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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.google.gson.Gson;

/**
 * Represents the name at the bottom of the names page, that users can train by associating an attribute with
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrainingName extends Name {
    private static final Gson GSON = new Gson();
    private NameAttribute attribute;

    public TrainingName(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
