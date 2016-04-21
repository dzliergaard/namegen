package com.rptools.city;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RacePop {
    private Species species;
    private int population;

    public RacePop(Species species, int population) {
        this.species = species;
        this.population = population;
    }

    public RacePop(Species species) {
        this.species = species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RacePop)) return false;

        RacePop racePop = (RacePop) o;
        return species == racePop.species;
    }
}
