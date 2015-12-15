package com.rptools.city;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Lists;
import com.rptools.util.RPEntity;

@Getter
@Setter
public class City extends RPEntity {
    private String name;
    private Ruler ruler;
    private Population population = new Population();
    private List<String> inns = Lists.newArrayList();

    public City() {
    }
}
