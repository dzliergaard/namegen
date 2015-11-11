package com.rptools.name;

import com.google.common.collect.Lists;

import java.util.List;

public enum NameAttribute {
    DEVIOUS,
    DUMB,
    EXOTIC,
    GRUFF,
    NOBLE,
    SKEEVY,
    SMART,
    SKIP;

    public static List<NameAttribute> asList(){
        return Lists.newArrayList(NameAttribute.values());
    }
}
