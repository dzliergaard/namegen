import {Component, Inject} from "angular2/core";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {UserData} from "app/user-data";
import {MDL_COMPONENTS} from "mat/materials";
import {NameStore} from "name/name-store";

@Component({
    selector: '[name-generate-form]',
    directives: [CORE_DIRECTIVES, FORM_DIRECTIVES, MDL_COMPONENTS],
    templateUrl: 'templates/name/name-generate-form.component.html'
})
export class NameGenerateForm {
    constructor(@Inject(NameStore) private nameStore:NameStore, @Inject(UserData) private userData:UserData) {
    }

    generate (numNames?:number) {
        this.userData.generating = true;
        this.nameStore.generate(numNames).subscribe(res => {
            this.userData.generatedNames = res;
            this.userData.generating = false;
        });
    }
}