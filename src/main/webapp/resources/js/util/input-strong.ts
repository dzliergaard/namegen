import {AfterViewChecked, Component, Directive, ElementRef, Inject, Input, ViewChild} from "angular2/core";
import {FORM_DIRECTIVES} from "angular2/common";

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

@Directive({
    selector: 'input'
})
class InputElement {
    constructor(@Inject(ElementRef) public elementRef:ElementRef){
    }
}

@Component({
    selector: '[input-strong]',
    directives: [InputElement, FORM_DIRECTIVES],
    template: `
        <strong class="mdl-cell mdl-cell--12-col" [hidden]="item.isEditing()" (click)="select = true; item.edit()">
            {{item.text}}
        </strong>
        <input class="mdl-cell mdl-cell--12-col mdl-textfield__input mdl-textfield--expandable" [hidden]="!item.isEditing()" [(ngModel)]="item.text" (keyup.enter)="doDone(input)" (blur)="doDone()"/>
    `
})
export class InputStrong implements AfterViewChecked {
    @Input() private item:InputStrongData;
    @ViewChild(InputElement) private input:InputElement;
    private select:boolean = false;

    constructor() {
    }

    ngAfterViewChecked() {
        if (this.select) {
            this.input.elementRef.nativeElement.select();
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