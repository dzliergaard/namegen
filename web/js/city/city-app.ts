import {bootstrap, provide, Component, ElementRef, OnInit, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import {City, Population, RacePop} from './city';
import {CityForm, CityFormComponent} from './city-form';
import {CityTable} from './city-table';
import {CityStore} from './city-store';

@Component({
    selector: '[city-app]',
    directives: [CityFormComponent, CityTable, CORE_DIRECTIVES, FORM_DIRECTIVES],
    template: `
        <div class="row">
            <city-form (generate)="generate()"
                       [form]="cityForm"
                       [state]="state"
                       [diversity-values]="diversityValues"
                       [size-values]="sizeValues"
                       [species-values]="speciesValues"></city-form>
        </div>
        <div class="row" *ng-if="city.name">
            <city [city]="city" class="col-xs-6" (refresh-attr)="generate($event)"></city>
        </div>
    `
})
export class CityComponent implements OnInit {
    state = {generating: false};
    cityForm:CityForm = new CityForm();
    diversityValues:Array<string>;
    sizeValues:Array<string>;
    speciesValues:Array<string>;

    constructor(public cityStore:CityStore, public city:City, private elementRef:ElementRef) {
    };

    onInit() {
        const nativeElement = this.elementRef.nativeElement;
        this.diversityValues = nativeElement.getAttribute('diversity-values').match(/(\w+)/g);
        this.sizeValues = nativeElement.getAttribute('size-values').match(/(\w+)/g);
        this.speciesValues = nativeElement.getAttribute('species-values').match(/(\w+)/g);
    }

    generate(attr:string) {
        this.state.generating = true;
        this.cityStore.generate(this.cityForm.size, this.cityForm.race, this.cityForm.diversity, attr).subscribe(res => {
            this.state.generating = false;
        });
    }
}

bootstrap(CityComponent, [CityStore, provide(City, {useValue: new City(null, null, null, null)})]);