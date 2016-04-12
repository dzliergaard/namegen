import {Component, Optional, Inject} from "angular2/core";
import {CORE_DIRECTIVES} from "angular2/common";
import {Name, GeneratedName, SavedName} from "name/name";
import {InputStrong} from "util/input-strong";

@Component({
    selector: '[dz-name-line]',
    directives: [InputStrong, CORE_DIRECTIVES],
    template: `
        <input-strong class="col-xs-8" [(item)]="name" (done)="name.doSecondary(name)"></input-strong>
        <button class="btn col-xs-4" (mousedown)="btnClick()" [disabled]="name.btnDisabled()">
            {{btnContent()}}
            <img class="loading" *ngIf="name.isSaving()" src="/images/loading.gif" height="30px" width="30px">
        </button>
    `
})
export class NameLine {
    name:Name;
    constructor(@Inject(GeneratedName) @Optional() generatedName:GeneratedName,
                @Inject(SavedName) @Optional() savedName:SavedName) {
        if (generatedName) {
            this.name = generatedName;
        } else if (savedName) {
            this.name = savedName;
        }
    }

    private btnClick() {
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