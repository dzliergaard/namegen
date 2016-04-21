import {Component, EventEmitter, Output, Inject, Self} from "angular2/core";
import {CORE_DIRECTIVES} from "angular2/common";
import {City} from "city/city";

@Component({
    selector: 'table[city]',
    directives: [CORE_DIRECTIVES],
    templateUrl: 'templates/city/city-table.component.html'
})
export class CityTable {
    @Output('refresh-attr') refreshAttr = new EventEmitter();

    constructor(@Inject(City) @Self() private city:City){
    }

    formatRuler() {
        return [this.city.ruler.name.text + ' (' + this.city.ruler.species + ')'];
    }

    formatPopulation() {
        let population = ["Total: " + this.city.population.tot];
        population.push.apply(population, this.city.population.people.map(racePop => racePop.species + ' (' + racePop.number + ')'));
        return population
    }
}