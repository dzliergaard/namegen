package com.rptools.city;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RacePop {
    private Race race;
    private int population;

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
