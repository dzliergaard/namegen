package com.rptools.name;

import java.util.Map;

public class Names {
    protected Map<String, Integer> beg;
    protected Map<String, Integer> mid;
    protected Map<String, Integer> end;
    protected double mean;
    protected double deviation;

    public Names(
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
