package com.rptools.city;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public enum Race {
    Human,
    Elf,
    Dwarf,
    Gnome,
    Orc,
    Goliath,
    Dragonborn,
    Halfling;

    private static final Random rand = new Random();

    public static Race rand() {
        return values()[rand.nextInt(values().length)];
    }

    public static Race get(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return rand();
        }
    }

    public static List<Race> asList() {
        return Lists.newArrayList(Race.values());
    }
}
