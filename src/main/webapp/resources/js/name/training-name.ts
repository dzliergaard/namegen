import {Component, Inject} from "angular2/core";
import {NameStore} from "./name-store";
import $ = require("jquery");

/**
 * The training data name at the bottom of the names screen.
 * Users may associate an attribute they feel the name exemplifies.
 * One day this training data will be used to be able to generate
 * names that are more like the specified attributes.
 */
@Component({
    selector: '[training-name]',
    template: `
        <div class="col-xs-12">
            <strong class="col-xs-4">{{nameData.text}}</strong>
            <button class="col-xs-1 btn" *ngFor="#attr of nameAttributes" (click)="trainName(attr)">
                {{attr}}
            </button>
        </div>
    `
})
export class TrainingName {
    private nameAttributes:string[] = $('meta#name-attributes').attr('content').match(/\w+/g);

    public nameData:any = {};

    constructor(@Inject(NameStore) private nameStore:NameStore){
        this.nameData.text = JSON.parse($('meta#training-name').attr('content')).text;
    }

    private trainName(attr:string) {
        this.nameData.attribute = attr;
        return this.nameStore.train(this.nameData).subscribe(res => {
            this.nameData.text = res.text;
            this.nameData.attribute = null;
        });
    }
}