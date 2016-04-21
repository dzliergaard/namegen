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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;

/**
 * An {@link ExclusionStrategy} to prevent {@Gson} from parsing some source files
 */
public class GsonExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getDeclaredClass().equals(UserDrive.class) ||
               fieldAttributes.getDeclaredClass().equals(Gson.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
