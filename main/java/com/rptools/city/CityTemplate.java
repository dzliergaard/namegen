package com.rptools.city;

import java.util.Random;

public enum CityTemplate {
    Village(50, 500),
    Town(500, 5000),
    City(5000, 25000),
    Capital(25000, 75000);

    private static final Random rand = new Random();

    private int min, max;

    CityTemplate(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int pop() {
        return rand.nextInt(max - min) + min;
    }

    public static CityTemplate rand() {
        return values()[rand.nextInt(values().length)];
    }

    public static CityTemplate get(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return rand();
        }
    }
}
