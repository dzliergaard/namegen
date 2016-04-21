import {Directive, Inject} from "angular2/core";
import {UserData} from "app/user-data";
import {NameStore} from "name/name-store";
import {InputStrongData} from "util/input-strong";
import _ = require("underscore");

/**
 * Base class represents the *concept* of a name, not an actual URI element.
 */
export class Name extends InputStrongData {
    public id:string;
    public text:string;
    protected lastSavedText:string;

    constructor(@Inject(NameStore) private nameStore:NameStore) {
        super();
    }

    protected saveName() {
        this.save();
        this.nameStore.save(this.nameData).subscribe(n => this.saveCallback(n), e => this.saveError(e));
    }

    protected saveCallback(response:any) {
        this.doneSaving();
        this.text = response.text;
        this.lastSavedText = response.text;
    }

    private saveError(error:any) {
        console.log("Error attempting to save name: " + this.text + "\n" + error);
    }

    doPrimary() {
    }
    
    doSecondary() {
    }

    btnText() {
        return ""
    }

    btnDisabled() {
        return false;
    }

    get nameData() {
        return {
            id: this.id,
            text: this.text
        };
    }

    set nameData(nameData:any) {
        this.id = nameData.id;
        this.text = nameData.text;
        this.lastSavedText = nameData.text;
    }
}

@Directive({
    selector: '[dz-generated-name]',
    inputs: ['nameData: dz-generated-name']
})
export class GeneratedName extends Name {
    constructor(@Inject(NameStore) private nameStore:NameStore,
                @Inject(UserData) private userData:UserData) {
        super(nameStore);
    }

    protected saveCallback(response:any) {
        super.saveCallback(response);
        this.userData.savedNames.push(response);
        console.log("New name saved: " + JSON.stringify(response));
    }

    doPrimary() {
        return this.saveName();
    }

    btnText() {
        return this.isEditing() ? "Done" : "Save";
    }

    public btnDisabled() {
        return !this.isEditing() && !this.userData.isSignedIn;
    }
}

@Directive({
    selector: '[dz-saved-name]',
    inputs: ['nameData: dz-saved-name']
})
export class SavedName extends Name {
    constructor(@Inject(NameStore) nameStore:NameStore,
                @Inject(UserData) private userData:UserData) {
        super(nameStore);
    }

    protected saveCallback(response:any) {
        super.saveCallback(response);
        this.id = response.id;
        console.log("Name updated: " + JSON.stringify(response));
    }

    doneEditing() {
        super.doneEditing();
        this.doSecondary();
    }

    doPrimary() {
        this.save();
        return this.nameStore.remove(this.nameData).subscribe(() => {
            this.userData.savedNames = _.reject(this.userData.savedNames, n => n.id == this.id);
        }, err => this.doneSaving());
    }

    doSecondary() {
        if (this.text != this.lastSavedText) {
            this.saveName();
        }
    }

    btnText() {
        return this.isEditing() ? "Done" : "Remove";
    }
}