package com.rptools.city;

import java.util.List;

import com.google.common.collect.Lists;
import com.rptools.util.RPEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City extends RPEntity {
    private String name;
    private String ruler;
    private Population population = new Population();
    private List inns = Lists.newArrayList();

    public City() {
    }
}
