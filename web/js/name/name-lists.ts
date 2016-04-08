import {Component, Inject} from "angular2/core";
import {GeneratedName, SavedName} from "name/name";
import {NameLine} from "name/name-line";
import {UserContent} from "app/user-content";

@Component({
    selector: '[name-lists]',
    directives: [GeneratedName, SavedName, NameLine],
    templateUrl: 'templates/name/name-lists.component.html'
})
export class NameLists {
    constructor(@Inject(UserContent) private userContent:UserContent) {
    }
}