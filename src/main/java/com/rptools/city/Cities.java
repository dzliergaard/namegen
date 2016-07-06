/*
 *  RPToolkit - Tools to assist Role-Playing Game masters and players
 *  Copyright (C) 2016  Dane Zeke Liergaard
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rptools.city;

import lombok.Data;

import java.util.List;

/**
 * Represents the parsed city data from cityData.json.
 * An inn is a pattern formed by {begPat} {endPat}, where either will have placeholders for
 * things like adjectives, names, and qualifiers
 */
@Data
public class Cities {
    private Inns inns;
    private Guilds guilds;

    @Data
    public static class Inns {
        private List<String> begPat;
        private List<String> beg;
        private List<String> endPat;
        private List<String> end;
    }

    @Data
    public static class Guilds {
        private List<String> pat;
        private List<String> group;
        private List<String> noun;
    }
}
