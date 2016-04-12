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

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.rptools.util.WeightedList;

/**
 * A {@link WeightedList} of groups that appear at the beginning, middle, and end of analyzed names.
 */
public class WeightedNames {
    WeightedList<String> beg;
    WeightedList<String> mid;
    WeightedList<String> end;
    SummaryStatistics stats;

    public WeightedNames(Names names, SummaryStatistics stats) {
        this.stats = stats;
    }

    public int syllables() {
        return (int) Math.round(new Random().nextGaussian() * stats.getStandardDeviation() + stats.getMean());
    }
}
