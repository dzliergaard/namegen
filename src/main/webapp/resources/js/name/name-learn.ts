import {Component} from "angular2/core";
import {TrainingName} from "name/training-name";
import {MDL_COMPONENTS} from "mat/materials";

@Component({
    selector: '[name-learn]',
    directives: [TrainingName, MDL_COMPONENTS],
    templateUrl: 'templates/name/name-learn.component.html'
})
export class NameLearn {
}