import {Component, Inject} from "angular2/core";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {UserData} from "app/user-data";
import {CityStore} from "city/city-store";
import {MDL_COMPONENTS} from "mat/materials";
import {FormRadio} from "util/form-radio";

@Component({
    selector: '.city-form',
    directives: [FormRadio, CORE_DIRECTIVES, FORM_DIRECTIVES, MDL_COMPONENTS],
    templateUrl: 'templates/city/city-form.component.html'
})
export class CityForm {
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
                @Inject(UserData) private userData:UserData) {
        cityStore.variables().subscribe(res => {
            this.diversityData.values = res.diversityValues;
            this.sizeData.values = res.sizeValues;
            this.speciesData.values = res.speciesValues;
        });
    }

    private generate() {
        this.userData.generating = true;
        this.cityStore.generate(this.sizeData.value, this.speciesData.value, this.diversityData.value).subscribe(res => {
            this.userData.generatedCity = res;
            this.userData.generating = false;
        });
    }
}