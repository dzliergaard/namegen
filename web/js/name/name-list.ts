import {Component, EventEmitter, Output, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import {NameLine} from './name-line';
import {Name} from './name';

@Component({
    selector: 'name-list',
    properties: ['names', 'btnText', 'disabled'],
    template: `
        <name-line *ng-for="#name of names"
                   class="col-xs-12"
                   [name]="name"
                   [btn-text]="btnText"
                   [disabled]="disabled"
                   (primary)="primary.next($event)"
                   (secondary)="secondary.next($event)"/>
    `,
    directives: [NameLine, CORE_DIRECTIVES, FORM_DIRECTIVES]
})
export class NameList {
    @Output() primary = new EventEmitter();
    @Output() secondary = new EventEmitter();
}