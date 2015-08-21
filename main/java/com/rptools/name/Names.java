package com.rptools.name;

import java.util.Map;

public class Names {
    protected Map<String, Integer> beg;
    protected Map<String, Integer> mid;
    protected Map<String, Integer> end;

    public Names(Map<String, Integer> beg, Map<String, Integer> mid, Map<String, Integer> end) {
        this.beg = beg;
        this.mid = mid;
        this.end = end;
    }
}
