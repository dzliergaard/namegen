import {Inject, Injectable, ng} from "angular2/core";
import {CityCalls} from "city/city-calls";
import _ = require('underscore');

@Injectable()
export class CityStore {
    constructor(@Inject(CityCalls) private caller:CityCalls) {
    }

    generate(size:string, species:string, diversity:string) {
        return this.caller.generate({
            size: size,
            species: species,
            diversity: diversity
        });
    }

    save(city:any) {
        return this.caller.save(city);
    }

    remove(city:any) {
        return this.caller.remove(city);
    }

    variables() {
        return this.caller.variables();
    }
}