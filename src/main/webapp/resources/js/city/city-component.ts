import {Component, Inject} from "angular2/core";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {UserData} from "app/user-data";
import {City} from "city/city";
import {CityCalls} from "city/city-calls";
import {CityForm} from "city/city-form";
import {CityStore} from "city/city-store";
import {CityTable} from "city/city-table";
import {Materials} from "util/materials";

@Component({
    selector: '[city-app]',
    directives: [City, CityForm, CityTable, CORE_DIRECTIVES, FORM_DIRECTIVES],
    templateUrl: 'templates/city/city.component.html',
    providers: [
        CityStore,
        CityCalls
    ]
})
export class CityComponent {
    private tableClass:string;
    constructor(@Inject(UserData) public userData:UserData, @Inject(Materials) materials:Materials) {
        this.tableClass = 'mdl-color--primary-dark mdl-color-text--accent ' +
            'mdl-data-table mdl-js-data-table mdl-shadow--2dp mdl-cell mdl-cell--4-col city-table';
    };
}