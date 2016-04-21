import {Directive} from "angular2/core";

@Directive({
    selector: '[city]',
    inputs: ['cityData: city']
})
export class City {
    private id:string;
    public name:string;
    public ruler:Ruler;
    public population:Population;
    public inns:Array<string>;

    set cityData(cityData:any) {
        if (!cityData) {
            return;
        }
        this.id = cityData.id;
        this.name = cityData.name;
        this.ruler = new Ruler(cityData.ruler);
        this.population = new Population(cityData.population);
        this.inns = cityData.inns;
    }
}

class Ruler {
    public name:string;
    public species:string;
    constructor(rulerData:any) {
        if (!rulerData) {
            return;
        }
        this.name = rulerData.name;
        this.species = rulerData.species;
    }
}

class RacePop {
    public species:string;
    public number:number;
    constructor(racePopData:any) {
        if (!racePopData) {
            return;
        }
        this.species = racePopData.species;
        this.number = racePopData.population;
    }
}

class Population {
    public people:Array<RacePop>;
    public tot:number;
    public searchMod:number;
    constructor(populationData:any) {
        if (!populationData) {
            return;
        }
        this.people = populationData.people.map(item => new RacePop(item));
        this.tot = populationData.tot;
        this.searchMod = populationData.searchMod;
    }
}