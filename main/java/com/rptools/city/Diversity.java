package com.rptools.city;

import java.util.Random;

public enum Diversity {
    Low(.1),
    Medium(.2),
    High(.3),
    Equal(.4);

    private static final Random rand = new Random();

    private double modifier;

    Diversity(double modifier) {
        this.modifier = modifier;
    }

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
}
