package com.rptools.name;

import java.util.Random;

import com.rptools.util.WeightedList;

public class WeightedNames {
    protected double mean;
    protected double deviation;
    protected WeightedList<String> beg;
    protected WeightedList<String> mid;
    protected WeightedList<String> end;

    public WeightedNames() {
    }

    public WeightedNames(Names names) {
        mean = names.mean;
        deviation = names.deviation;
        beg = WeightedList.fromItemToWeightMap(names.beg);
        mid = WeightedList.fromItemToWeightMap(names.mid);
        end = WeightedList.fromItemToWeightMap(names.end);
    }

    public int syllables(){
        return (int) Math.round(new Random().nextGaussian() * deviation + mean);
    }
}
