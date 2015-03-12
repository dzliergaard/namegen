package com.rptools.items;

import com.rptools.util.WeightedList;

public class WeightedNameData {
    double mean;
    double deviation;
    WeightedList<String> beg;
    WeightedList<String> mid;
    WeightedList<String> end;

    public WeightedNameData(NameData nameData) {
        mean = nameData.mean;
        deviation = nameData.deviation;
        beg = WeightedList.fromItemToWeightMap(nameData.beg);
        mid = WeightedList.fromItemToWeightMap(nameData.mid);
        end = WeightedList.fromItemToWeightMap(nameData.end);
    }
}
