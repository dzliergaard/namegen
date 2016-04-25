import {Component, Inject} from "angular2/core";
import {NameStore} from "name/name-store";
import {MDL_COMPONENTS} from "mat/materials";

/**
 * The training data name at the bottom of the names screen.
 * Users may associate an attribute they feel the name exemplifies.
 * One day this training data will be used to be able to generate
 * names that are more like the specified attributes.
 */
@Component({
    selector: '[training-name]',
    directives: [MDL_COMPONENTS],
    template: `
        <strong cell=8 d=4 class="mdl-cell--middle">{{nameData.text}}</strong>
        <button mdl-btn flat="true" cell=1 p=2 *ngFor="#attr of nameAttributes" (click)="trainName(attr)">{{attr}}</button>
    `
})
export class TrainingName {
    private nameAttributes:string[];
    public nameData:any = {};

    constructor(@Inject(NameStore) private nameStore:NameStore){
        nameStore.train(null).subscribe(res => this.trainNameCallback(res));
        nameStore.attributes().subscribe(res => this.nameAttributes = res);
    }

    private trainName(attr:string) {
        this.nameData.attribute = attr;
        return this.nameStore.train(this.nameData).subscribe(res => this.trainNameCallback(res));
    }

    private trainNameCallback(res:any) {
        this.nameData.text = res.text;
        this.nameData.attribute = null;
    }
}