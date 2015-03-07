package com.rptools.items;

import java.util.Random;

enum NameAttribute {
    MEAN("NICE"),
    SLEAZY("CLEAN"),
    COOL("UNCOOL");

    private String opposite;

    private static Random rand = new Random();

    NameAttribute(String opposite){
        this.opposite = opposite;
    }

    public String getOpposite(){
        return opposite;
    }

    static NameAttribute random() {
        return values()[rand.nextInt(NameAttribute.values().length)];
    }
}
