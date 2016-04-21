import {Component, Input, Inject} from "angular2/core";
import {CORE_DIRECTIVES} from "angular2/common";
import {Materials} from "util/materials";

@Component({
    selector: '[form-radio]',
    directives: [CORE_DIRECTIVES],
    templateUrl: 'templates/form-radio.component.html'
})
export class FormRadio {
    @Input('data') private data:any;
    private btnClass:string;
    private activeBtnClass:string;

    constructor(@Inject(Materials) private materials:Materials){
        this.btnClass = materials.btnClass([], materials.cellClass({d: 12, t: 8, p: 4}));
        this.activeBtnClass = "mdl-button--raised";
    }

    private setValue(value) {
        this.data.value = value;
    }
}