import {Component, Input, CORE_DIRECTIVES} from 'angular2/angular2';
import {NameStore} from './name-store';
import {Name} from './name';

@Component({
    selector: 'name-learn',
    directives: [CORE_DIRECTIVES],
    template: `
        <div class="alert learning col-xs-12">
            <div class="col-xs-12">
                <strong>Want to help train the name generator? Click the attribute that best describes the training name:</strong>
            </div>
            <div class="col-xs-12">
                <strong class="col-xs-4">{{trainingName.text}}</strong>
                <button class="col-xs-1 btn" *ng-for="#attribute of nameAttributes" (click)="trainName(attribute)">
                    {{attribute}}
                </button>
            </div>
        </div>
    `
})
export class NameLearn {
    @Input() trainingName: Name;
    @Input() nameAttributes: Array<String>;

    constructor (private nameStore:NameStore) {
    }

    trainName (attribute:string) {
        this.trainingName.attribute = attribute;
        this.nameStore.train(this.trainingName).subscribe(res => this.trainingName = res.json())
    }
}