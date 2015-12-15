import {AfterViewChecked, Component, EventEmitter, ElementRef, Input, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import $ = require('jquery');

@Component({
    selector: 'input-strong',
    outputs: ['edit', 'done'],
    directives: [CORE_DIRECTIVES, FORM_DIRECTIVES],
    template: `
        <strong class="col-xs-12" [hidden]="editing" (click)="doEdit(input)">{{item.text}}</strong>
        <input #input [hidden]="!editing" class="col-xs-12" (keyup.enter)="doDone()" [(ng-model)]="item.text" (blur)="doDone()"/>
    `,
    exportAs: 'inputstrong'
})
export class InputStrong implements AfterViewChecked {
    edit = new EventEmitter();
    done = new EventEmitter();
    select:boolean = true;
    public editing:boolean = false;
    input:ElementRef;
    @Input() item:any;

    afterViewChecked () {
        if (!this.input || !this.select) { return; }
        $(this.input).select();
        this.select = false;
    }

    doEdit (input:ElementRef) {
        this.input = input;
        this.editing = true;
        this.select = true;
        this.edit.next(this.item);
    }

    doDone () {
        this.editing = false;
        this.done.next(this.item);
    }
}