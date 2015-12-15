import {Component, EventEmitter, Output, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import {Name} from './name';
import {InputStrong} from '../util/input-strong';
import {NameComponent} from './name-app';

@Component({
    selector: 'name-line',
    properties: ['name', 'btnText', 'disabled'],
    template: `
        <input-strong #inputStrong class="col-xs-8" [item]="name"></input-strong>
        <button *ng-if="!inputstrong.editing" class="btn col-xs-4" (click)="primary.next(name)" [disabled]="disabled">{{btnText}}</button>
        <button *ng-if="inputstrong.editing" class="btn col-xs-4" (click)="doSecondary(inputstrong)">Done</button>
    `,
    directives: [InputStrong, CORE_DIRECTIVES, FORM_DIRECTIVES]
})
export class NameLine {
    @Output() primary = new EventEmitter();
    @Output() secondary = new EventEmitter();

    doSecondary (inputStrong:InputStrong) {
        inputStrong.doDone();
        this.secondary.next(name);
    }
}