import {Component, Optional, Inject} from "angular2/core";
import {CORE_DIRECTIVES} from "angular2/common";
import {MDL_COMPONENTS} from "mat/materials";
import {Name, GeneratedName, SavedName} from "name/name";
import {InputStrong} from "util/input-strong";

@Component({
    selector: '[dz-name-line]',
    directives: [InputStrong, CORE_DIRECTIVES, MDL_COMPONENTS],
    template: `
        <span input-strong primary class="mdl-cell--stretch" [(item)]=name (done)=name.doSecondary(name)></span>
        <button secondary cell=3 p=2 [mdl-btn]="savedButton ? 'primary': ''" [flat]="savedButton && name.isSaving()" 
                (mousedown)=btnClick($event) [disabled]=name.btnDisabled()>
            <span >{{btnContent()}}</span>
            <mdl-spinner [show]=name.isSaving()></mdl-spinner>
        </button>
    `
})
export class NameLine {
    private savedButton:boolean = false;
    private name:Name;
    constructor(@Inject(GeneratedName) @Optional() generatedName:GeneratedName,
                @Inject(SavedName) @Optional() savedName:SavedName) {
        this.name = generatedName ? generatedName : savedName;
        this.savedButton = !!savedName;
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