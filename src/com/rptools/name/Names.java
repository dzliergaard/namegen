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

import java.util.Map;

/**
 * This class is generated when the application starts and needs to read/parse/interpret data from the name files stored
 * in S3. It contains maps of "syllable" substrings to the frequency in which they appear, at the beginning, middle,
 * and end of the analyzed names. One of these objects is created for first names, one for last
 */
public class Names {
    final Map<String, Integer> beg;
    final Map<String, Integer> mid;
    final Map<String, Integer> end;

    public Names(Map<String, Integer> beg, Map<String, Integer> mid, Map<String, Integer> end) {
        this.beg = beg;
        this.mid = mid;
        this.end = end;
    }
}
