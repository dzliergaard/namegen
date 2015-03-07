package com.rptools.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedList<E> {
    //    private static Logger log = Logger.getLogger(WeightedList.class.getName());
    private final NavigableMap<Integer, E> map = new TreeMap<Integer, E>();
    private int total = 0;

    public WeightedList(){}

    @SuppressWarnings("unchecked")
    public WeightedList(Object... inits){
        int i = 0;
        while(inits[i] != null){
            add((Integer)inits[i++], (E)inits[i++]);
        }
    }

    public WeightedList(Map<E, ? extends Integer> map){
        for(Entry<E,? extends Integer> entry : map.entrySet()){
            add(entry.getValue(), entry.getKey());
        }
    }

    public void add(int weight, E item) {
        if (weight <= 0) {
            return;
        }
        total += weight;
        map.put(total, item);
    }

    public E get(int value) {
        if (map.ceilingEntry(value) != null) {
            return map.ceilingEntry(value).getValue();
        }
        return map.floorEntry(value).getValue();
    }

    public int size() {
        return map.size();
    }

    public E random() {
        int value = new Random().nextInt(total);
        return map.ceilingEntry(value).getValue();
    }

    public E remove(){
        return remove(new Random().nextInt(total));
    }

    public E remove(int value){
        Entry<Integer, E> entry = map.ceilingEntry(value);
        if(entry == null){
            entry = map.floorEntry(value);
        }
        total -= entry.getKey();
        return map.remove(entry.getKey());
    }

    public static <T> WeightedList<T> fromItemToWeightMap(Map<T, Integer> input) {
        WeightedList<T> result = new WeightedList<T>();
        for (Entry<T, Integer> entry : input.entrySet()) {
            result.add(entry.getValue(), entry.getKey());
        }
        return result;
    }

    public String toString() {
        return map.entrySet().toString();
    }
}
