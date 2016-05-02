import {Component, Inject} from "angular2/core";
import {MDL_COMPONENTS} from "mat/materials";
import {GeneratedName, SavedName} from "name/name";
import {NameLine} from "name/name-line";
import {UserData} from "app/user-data";

@Component({
    selector: '.name-lists',
    directives: [GeneratedName, SavedName, NameLine, MDL_COMPONENTS],
    templateUrl: 'templates/name/name-lists.component.html'
})
export class NameLists {
    constructor(@Inject(UserData) private userData:UserData) {
    }

    private getOffset(cols:number) {
        return !this.userData.generatedNames[0] && cols;
    }
}