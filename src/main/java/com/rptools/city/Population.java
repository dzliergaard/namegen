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

import java.util.*;

import lombok.Data;

import com.dzlier.weight.WeightedList;
import com.google.common.collect.Lists;

/**
 * Population of a city, consisting of up to three races, each with a number of inhabitants of the given race.
 */
@Data
public class Population {
    private static Random rand = new Random();
    private List<RacePop> people = Lists.newArrayList();
    private int tot = 0;
    private int searchMod;

    // search mods are static numbers based on number
    private static NavigableMap<Integer, Integer> searchMods = new TreeMap<Integer, Integer>() {
        {
            put(0, -6);
            put(500, -4);
            put(1500, -2);
            put(4500, 0);
            put(6750, 1);
            put(26500, 3);
            put(Integer.MAX_VALUE, 5);
        }
    };

    public void add(Double size, Double diversity, Species species) {
        if (people.contains(new RacePop(species))) {
            return;
        }

        int population = new Double(size * (rand.nextDouble() + .5)).intValue();
        population *= Math.pow(diversity, people.size());
        people.add(new RacePop(species, population));
        tot += population;
        searchMod = searchMods.ceilingEntry(tot).getValue();
    }

    /**
     * Checks whether number has 3 races, or sometimes 2 for low-number areas
     */
    public boolean incompletePop(Double diversity) {
        return people.size() < 3 && (people.size() < 2 || rand.nextDouble() * .2 < diversity);
    }

    /**
     * Gets a random Race from number, weighted based on number density
     */
    public String getWeightedRace() {
        WeightedList<Species> races = new WeightedList<>();
        for (RacePop rp : people) {
            races.add(rp.getPopulation() * 3.0, rp.getSpecies());
        }
        return races.random().name();
    }
}
