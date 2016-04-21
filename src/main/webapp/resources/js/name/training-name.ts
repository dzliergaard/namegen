import {Component, Inject} from "angular2/core";
import {NameStore} from "name/name-store";
import {Materials} from "util/materials";

/**
 * The training data name at the bottom of the names screen.
 * Users may associate an attribute they feel the name exemplifies.
 * One day this training data will be used to be able to generate
 * names that are more like the specified attributes.
 */
@Component({
    selector: '[training-name]',
    template: `
        <strong [class]="nameClass">{{nameData.text}}</strong>
        <button [class]="btnClass" *ngFor="#attr of nameAttributes" (click)="trainName(attr)">{{attr}}</button>
    `
})
export class TrainingName {
    private static btnCellClasses:any = {all: 1, p: 2};
    private static nameCellClasses:any = {all: 2, d: 4};
    private nameAttributes:string[];
    public nameData:any = {};
    private btnClass:string;
    private nameClass:string;

    constructor(@Inject(NameStore) private nameStore:NameStore, @Inject(Materials) materials:Materials){
        nameStore.train(null).subscribe(res => this.trainNameCallback(res));
        nameStore.attributes().subscribe(res => this.nameAttributes = res);
        this.btnClass = materials.btnClass(["flat"], materials.cellClass(TrainingName.btnCellClasses));
        this.nameClass = materials.cellClass(TrainingName.nameCellClasses, {}, "mdl-cell--middle");
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