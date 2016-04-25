import {Component, Input} from "angular2/core";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {MDL_COMPONENTS} from "mat/materials";

@Component({
    selector: '[form-radio]',
    directives: [CORE_DIRECTIVES, FORM_DIRECTIVES, MDL_COMPONENTS],
    templateUrl: 'templates/form-radio.component.html'
})
export class FormRadio {
    @Input('data') private data:any;
}