import {Component, Inject} from "angular2/core";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {UserData} from "app/user-data";
import {CityStore} from "city/city-store";
import {FormRadio} from "util/form-radio";
import {Materials} from "util/materials";

@Component({
    selector: '.city-form',
    directives: [FormRadio, CORE_DIRECTIVES, FORM_DIRECTIVES],
    templateUrl: 'templates/city/city-form.component.html'
})
export class CityForm {
    private static cardClasses:any = {main: 'primary', text: 'primary-contrast'};
    private btnClass:string;
    private radioClass:string;
    private diversityData:any = {
        heading: 'Diversity',
        value: ''
    };
    private sizeData:any = {
        heading: 'Size',
        value: ''
    };
    private speciesData:any = {
        heading: 'Dominant Species',
        value: ''
    };

    constructor(@Inject(CityStore) private cityStore:CityStore,
                @Inject(UserData) private userData:UserData,
                @Inject(Materials) private materials:Materials) {
        cityStore.variables().subscribe(res => {
            this.diversityData.values = res.diversityValues;
            this.sizeData.values = res.sizeValues;
            this.speciesData.values = res.speciesValues;
        });

        this.btnClass = materials.btnClass([], "mdl-cell mdl-cell--1-col");
        this.radioClass = materials.cellClass({all: 2}, {}, materials.cardClass(CityForm.cardClasses));
    }

    private generate() {
        this.userData.generating = true;
        this.cityStore.generate(this.sizeData.value, this.speciesData.value, this.diversityData.value).subscribe(res => {
            this.userData.generatedCity = res;
            this.userData.generating = false;
        });
    }
}