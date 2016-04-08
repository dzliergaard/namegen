import {AfterViewChecked, Component, ElementRef, Inject, Input} from "angular2/core";
import {FORM_DIRECTIVES} from "angular2/common";
import $ = require("jquery");

export class InputStrongData {
    protected editing:boolean = false;
    protected saving:boolean = false;

    public edit() {
        this.editing = true;
        this.saving = false;
    }

    public isEditing() {
        return this.editing;
    }

    public doneEditing() {
        this.editing = false;
    }

    public save() {
        this.editing = false;
        this.saving = true;
    }

    public isSaving() {
        return this.saving;
    }

    public doneSaving() {
        this.saving = false;
    }
}

@Component({
    selector: 'input-strong',
    directives: [FORM_DIRECTIVES],
    template: `
        <strong class="col-xs-12" [hidden]="item.isEditing()" (click)="select = true; item.edit()">
            {{item.text}}
        </strong>
        <input #input class="col-xs-12" [hidden]="!item.isEditing()" [(ngModel)]="item.text" (keyup.enter)="doDone()" (blur)="doDone()"/>
    `
})
export class InputStrong implements AfterViewChecked {
    @Input() private item:InputStrongData;
    private select:boolean = false;

    constructor(@Inject(ElementRef) private elementRef:ElementRef) {
    }

    ngAfterViewChecked() {
        if (this.select) {
            $(this.elementRef.nativeElement).find('input').select();
            this.select = false;
        }
    }

    doDone() {
        this.select = false;
        if (this.item.isEditing()) {
            this.item.doneEditing();
        }
    }
}