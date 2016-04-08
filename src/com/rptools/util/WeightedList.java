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

package com.rptools.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Implementation of a weighted list to choose randomly from.
 * 
 * @param <E>
 *            The elements to choose from
 */
public class WeightedList<E> {
    private final NavigableMap<Integer, E> map = new TreeMap<>();
    private int total = 0;

    public WeightedList() {
    }

    public void add(int weight, E item) {
        if (weight <= 0) {
            return;
        }
        total += weight;
        map.put(total, item);
    }

    public E get(int value) {
        if (map.ceilingEntry(value) != null) {
            return map.ceilingEntry(value).getValue();
        }
        return map.floorEntry(value).getValue();
    }

    public int size() {
        return map.size();
    }

    public E random() {
        int value = new Random().nextInt(total);
        return map.ceilingEntry(value).getValue();
    }

    public E remove() {
        return remove(new Random().nextInt(total));
    }

    public E remove(int value) {
        Entry<Integer, E> entry = map.ceilingEntry(value);
        if (entry == null) {
            entry = map.floorEntry(value);
        }
        total -= entry.getKey();
        return map.remove(entry.getKey());
    }

    public static <T> WeightedList<T> fromItemToWeightMap(Map<T, Integer> input) {
        WeightedList<T> result = new WeightedList<>();
        for (Entry<T, Integer> entry : input.entrySet()) {
            result.add(entry.getValue(), entry.getKey());
        }
        return result;
    }

    public String toString() {
        return map.entrySet().toString();
    }
}
