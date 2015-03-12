package com.rptools.items;

import java.util.Map;

public class NameData {
    Map<String, Integer> beg;
    Map<String, Integer> mid;
    Map<String, Integer> end;
    double mean;
    double deviation;

    public NameData(
            Map<String, Integer> beg,
            Map<String, Integer> mid,
            Map<String, Integer> end,
            double mean,
            double deviation) {
        this.beg = beg;
        this.mid = mid;
        this.end = end;
        this.mean = mean;
        this.deviation = deviation;
    }
}
