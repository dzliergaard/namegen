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

import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Implementation of a weighted list to choose randomly from. Extends TreeMap
 * 
 * @param <E>
 *            The element type to choose from
 */
public class WeightedList<E> extends TreeMap<Integer, E> {
    public WeightedList() {
    }

    public void add(int weight, E item) {
        if (weight <= 0) {
            return;
        }
        Integer integer = isEmpty() ? 0 : floorKey(Integer.MAX_VALUE);
        super.put(integer + weight, item);
    }

    public E get(int value) {
        if (ceilingEntry(value) != null) {
            return ceilingEntry(value).getValue();
        }
        return super.floorEntry(value).getValue();
    }

    public E random() {
        int value = new Random().nextInt(floorKey(Integer.MAX_VALUE));
        return ceilingEntry(value).getValue();
    }

    public String toString() {
        return entrySet().toString();
    }

    Stream<E> valueStream() {
        return this.values().stream();
    }
}
