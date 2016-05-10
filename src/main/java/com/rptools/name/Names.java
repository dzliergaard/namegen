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

import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.dzlier.weight.WeightedTrie;

/**
 * This class is generated when the application starts and needs to read/parse/interpret data from the name files stored
 * in S3. It contains maps of "group" substrings to the frequency in which they appear at the beginning, middle, and end
 * of the parsed names. One of these objects is created for first names, one for last
 */
public class Names {
    private final Double standardDeviation;
    private final Double mean;
    WeightedTrie<String> beg;
    WeightedTrie<String> mid;
    WeightedTrie<String> end;

    public Names(
            WeightedTrie<String> beg,
            WeightedTrie<String> mid,
            WeightedTrie<String> end,
            SummaryStatistics stats) {
        this.beg = beg;
        this.mid = mid;
        this.end = end;
        this.standardDeviation = stats.getStandardDeviation();
        this.mean = stats.getMean();
    }

    String makeName() {
        int groups = groups();

        String grandparent = beg.random("");
        String parent = beg.random("", grandparent);
        String name = grandparent + parent;

        while (groups-- > 1) {
            String group = Optional
                .of(mid.random("", grandparent, parent))
                .map(StringUtils::stripToNull)
                .orElse(mid.random("", parent));
            name += group;
            grandparent = parent;
            parent = group;
        }

        name += end.random("", grandparent, parent);

        return name;
    }

    public int groups() {
        return (int) Math.round(new Random().nextGaussian() * standardDeviation + mean);
    }
}
