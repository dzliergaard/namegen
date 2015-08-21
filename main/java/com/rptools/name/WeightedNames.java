package com.rptools.name;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.rptools.util.WeightedList;

public class WeightedNames {
    protected WeightedList<String> beg;
    protected WeightedList<String> mid;
    protected WeightedList<String> end;
    protected SummaryStatistics stats;

    public WeightedNames() {
    }

    public WeightedNames(Names names, SummaryStatistics stats) {
        beg = WeightedList.fromItemToWeightMap(names.beg);
        mid = WeightedList.fromItemToWeightMap(names.mid);
        end = WeightedList.fromItemToWeightMap(names.end);
        this.stats = stats;
    }

    public int syllables() {
        return (int) Math.round(new Random().nextGaussian() * stats.getStandardDeviation() + stats.getMean());
    }
}
