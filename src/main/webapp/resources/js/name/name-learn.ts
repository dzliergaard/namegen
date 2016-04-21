import {Component, Inject} from "angular2/core";
import {TrainingName} from "name/training-name";
import {Materials} from "util/materials";

@Component({
    selector: '[name-learn]',
    directives: [TrainingName],
    templateUrl: 'templates/name/name-learn.component.html'
})
export class NameLearn {
    private static cardClasses:any = {main:"primary-dark"};
    private static cellClasses:any = {d: 11, t: 7, p: 4};
    private cellClass:string;
    constructor(@Inject(Materials) materials:Materials){
        this.cellClass = materials.cardClass(NameLearn.cardClasses, materials.cellClass(NameLearn.cellClasses));
    }
}