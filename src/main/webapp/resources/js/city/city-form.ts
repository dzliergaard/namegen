import {Component, EventEmitter, Input, Output} from '../node_modules/angular2/core.d.ts';
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from '../node_modules/angular2/common.d.ts';
import {City} from './city';
import {FormRadio} from '../util/form-radio';

export class CityForm {
    constructor(public size:string, public race:string, public diversity:string) {
    }
}

@Component({
    selector: 'city-form',
    directives: [FormRadio, CORE_DIRECTIVES, FORM_DIRECTIVES],
    template: `
        <form class='form-inline'>
            <fieldset>
                <legend>City Generator</legend>
                <div class='row'>
                    <div form-radio [form]="form" [field-name]="'size'" [values]="sizeValues" [heading]="'Size'"></div>
                    <div form-radio [form]="form" [field-name]="'race'" [values]="speciesValues" [heading]="'Dominant Species'"></div>
                    <div form-radio [form]="form" [field-name]="'diversity'" [values]="diversityValues" [heading]="'Diversity'"></div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <button class="btn generate-button" (click)="generate.next()">
                            <span>Generate</span>
                        </button>
                        <img *ngIf="state.generating" src="/resources/static/loading.gif" height="20px" width="20px">
                    </div>
                </div>
            </fieldset>
        </form>
    `
})
export class CityFormComponent {
    @Input() form:CityForm;
    @Input() diversityValues:Array<string>;
    @Input() sizeValues:Array<string>;
    @Input() speciesValues:Array<string>;
    @Input() state:any;
    @Output() generate = new EventEmitter();
}