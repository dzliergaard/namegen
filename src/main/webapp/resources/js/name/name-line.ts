import {Component, Optional, Inject, AfterViewInit, ElementRef} from "angular2/core";
import {CORE_DIRECTIVES} from "angular2/common";
import {Name, GeneratedName, SavedName} from "name/name";
import {InputStrong} from "util/input-strong";
import {Materials} from "util/materials";

@Component({
    selector: '[dz-name-line]',
    directives: [InputStrong, CORE_DIRECTIVES],
    template: `
        <span input-strong class="mdl-list__item-primary-content mdl-cell--stretch" [(item)]="name" (done)="name.doSecondary(name)"></span>
        <button [class]="btnClass()" (mousedown)="btnClick($event)" [disabled]="name.btnDisabled()">
            <span >{{btnContent()}}</span>
            <div [class]="spinnerClass()"></div>
        </button>
    `
})
export class NameLine implements AfterViewInit {
    private saveButton:boolean = false;
    private name:Name;
    private btnBaseClass:string;
    constructor(@Inject(GeneratedName) @Optional() generatedName:GeneratedName,
                @Inject(SavedName) @Optional() savedName:SavedName,
                @Inject(Materials) private materials:Materials,
                @Inject(ElementRef) private elementRef:ElementRef) {
        this.name = generatedName ? generatedName : savedName;
        this.saveButton = !!savedName;
        var opts = ['flat'];
        if (this.saveButton){
            opts.push('primary');
        }
        let btnCellClass = materials.cellClass({all:2}, {}, 'mdl-list__item-secondary-action');
        this.btnBaseClass = this.materials.btnClass(opts, btnCellClass);
    }

    ngAfterViewInit() {
        componentHandler.upgradeElements(this.elementRef.nativeElement.children);
    }

    private btnClass() {
        let ret = [this.btnBaseClass];
        if (!this.saveButton || !this.name.isSaving()) {
            ret.push('mdl-button--raised');
        }
        return ret.join(" ");
    }

    private spinnerClass() {
        let ret = ["mdl-spinner mdl-js-spinner mdl-spinner--single-color is-upgraded"];
        if (this.name.isSaving()) {
            ret.push('is-active');
        } else {
            ret.push('hidden');
        }
        return ret.join(" ");
    }

    private btnClick($event) {
        if ($event.button !== 0) {
            return;
        }
        return this.name.isEditing() ? this.name.doSecondary() : this.name.doPrimary();
    }

    private btnContent() {
        if (this.name.isSaving()) {
            return "";
        } else if (!this.name.isEditing()) {
            return this.name.btnText()
        } else {
            return "Done";
        }
    }
}