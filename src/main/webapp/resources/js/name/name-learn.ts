import {Component} from "angular2/core";
import {TrainNameDirective} from "name/name-store-directive";
import {TrainingName} from "name/training-name";

@Component({
    selector: '[name-learn]',
    directives: [TrainNameDirective, TrainingName],
    templateUrl: 'templates/name/name-learn.component.html'
})
export class NameLearn {
}