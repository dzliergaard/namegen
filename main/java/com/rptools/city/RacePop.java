package com.rptools.city;

public class RacePop {
    public Race race;
    public int population;

    public RacePop(Race race, int population) {
        this.race = race;
        this.population = population;
    }

    public RacePop(Race race) {
        this.race = race;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RacePop)) return false;

        RacePop racePop = (RacePop) o;
        return race == racePop.race;
    }
}
