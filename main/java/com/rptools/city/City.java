package com.rptools.city;

import java.util.List;

import com.google.common.collect.Lists;
import com.rptools.util.RPEntity;

public class City extends RPEntity {
    public String name;
    public String ruler;
    public Population population = new Population();
    public List inns = Lists.newArrayList();

    public City() {
    }

    @Override
    public String getType() {
        return "City";
    }
}
