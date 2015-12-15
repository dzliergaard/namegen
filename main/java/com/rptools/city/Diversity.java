package com.rptools.city;

import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;

import com.google.common.collect.Lists;

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
