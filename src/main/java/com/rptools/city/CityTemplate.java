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

package com.rptools.city;

import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;

import com.google.common.collect.Lists;

/**
 * Enum useful for defining different sizes of towns/cities
 */
@RequiredArgsConstructor
public enum CityTemplate {
    Village(50, 500),
    Town(500, 5000),
    City(5000, 25000),
    Capital(25000, 75000);

    private static final Random rand = new Random();

    private final int min, max;

    public int pop() {
        return rand.nextInt(max - min) + min;
    }

    public static CityTemplate rand() {
        return values()[rand.nextInt(values().length)];
    }

    public static CityTemplate get(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return rand();
        }
    }

    public static List<CityTemplate> asList() {
        return Lists.newArrayList(CityTemplate.values());
    }
}
