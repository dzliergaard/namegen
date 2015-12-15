import {ng, Injectable} from 'angular2/angular2';
import {RequestOptions} from 'angular2/http';
import {EntityStore} from '../util/entity-store';
import {City} from './city';

@Injectable()
export class CityStore extends EntityStore {
    newCity:City;

    constructor(private city:City) {
        super("city");
    }

    updateCity() {
        this.updateAttr('name');
        this.updateAttr('ruler');
        this.updateAttr('population');
        this.updateAttr('inns');
    }

    updateAttr(attr:string) {
        this.city[attr] = this.newCity[attr];
    }

    generate(size:string, race:string, diversity:string, attr:string) {
        var answer = super.generate({
            size: size,
            race: race,
            diversity: diversity
        });
        answer.subscribe(res => {
            this.generating = false;
            this.newCity = res.json();
            if (attr) {
                this.updateAttr(attr);
            } else {
                this.updateCity();
            }
        });
        return answer;
    }
}