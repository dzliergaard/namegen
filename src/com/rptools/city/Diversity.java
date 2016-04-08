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
 * Enum representing levels of diversity within a city
 */
@RequiredArgsConstructor
public enum Diversity {
    Low(.1),
    Medium(.2),
    High(.3),
    Equal(.4);

    private static final Random rand = new Random();

    private final double modifier;

    public double mod() {
        return modifier;
    }

    public static Diversity rand() {
        return values()[rand.nextInt(values().length)];
    }

    public static Diversity get(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return rand();
        }
    }

    public static List<Diversity> asList() {
        return Lists.newArrayList(Diversity.values());
    }
}
