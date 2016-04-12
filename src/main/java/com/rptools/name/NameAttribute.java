package com.rptools.name;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Attributes that users can associate with names to help train the generator. To be used (much) later, eventually so
 * users can generate names with particular tone
 */
public enum NameAttribute {
    DEVIOUS,
    DUMB,
    EXOTIC,
    GRUFF,
    NOBLE,
    SKEEVY,
    SMART,
    SKIP;

    public static List<NameAttribute> asList() {
        return Lists.newArrayList(NameAttribute.values());
    }
}
