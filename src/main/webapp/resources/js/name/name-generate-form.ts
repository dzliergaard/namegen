import {Component, Inject} from "angular2/core";
import {FORM_DIRECTIVES} from "angular2/common";
import {UserContent} from "app/user-content";
import {NameStore} from "name/name-store";

@Component({
    selector: '[name-generate-form]',
    directives: [FORM_DIRECTIVES],
    templateUrl: 'templates/name/name-generate-form.component.html'
})
export class NameGenerateForm {
    constructor(@Inject(UserContent) private userContent, @Inject(NameStore) private nameStore:NameStore) {
    }

    generate (numNames?:number) {
        this.userContent.generating = true;
        this.nameStore.generate(numNames).subscribe(res => {
            this.userContent.generatedNames = res;
            this.userContent.generating = false;
        });
    }
}