import {Component} from "angular2/core";
import {CORE_DIRECTIVES} from "angular2/common";
import {NameCalls} from "name/name-calls";
import {NameGenerateForm} from "name/name-generate-form";
import {NameLearn} from "name/name-learn";
import {NameLists} from "name/name-lists";
import {NameStore} from "name/name-store";
import {TrainingName} from "name/training-name";

@Component({
    selector: '[name-app]',
    templateUrl: 'templates/name/name.component.html',
    directives: [
        NameGenerateForm,
        NameLearn,
        NameLists,
        TrainingName,
        CORE_DIRECTIVES
    ],
    providers: [
        NameCalls,
        NameStore
    ]
})
export class NameComponent {
}
