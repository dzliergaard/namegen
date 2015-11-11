package com.rptools.city;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.rptools.util.WeightedList;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Population {
    private static Random rand = new Random();
    private List<RacePop> people = Lists.newArrayList();
    private int tot = 0;
    private String searchMod;

    // search mods are static numbers based on population
    private static WeightedList<String> searchMods = new WeightedList<String>() {
        {
            this.add(250, "-6");
            this.add(500, "-4");
            this.add(1500, "-2");
            this.add(4500, "0");
            this.add(6750, "+1");
            this.add(26500, "+3");
            this.add(34500, "+5");
        }
    };

    public void add(Race race, CityTemplate template, Diversity diversity) {
        if (people.contains(new RacePop(race))) {
            return;
        }
        int population = template.pop();
        population *= Math.pow(diversity.mod(), people.size());
        people.add(new RacePop(race, population));
        tot += population;
        searchMod = searchMods.get(tot);
    }

    /**
     * Checks whether population has 3 races, or sometimes 2 for low-population areas
     */
    public boolean completePop(Diversity diversity) {
        return people.size() < 3 && (people.size() < 2 || rand.nextDouble() * .2 < diversity.mod());
    }

    /**
     * Gets a random Race from population, weighted based on population density
     */
    public String getWeightedRace() {
        WeightedList<Race> races = new WeightedList<Race>();
        for (RacePop rp : people) {
            races.add(rp.getPopulation() * 3, rp.getRace());
        }
        return races.random().name();
    }
}
