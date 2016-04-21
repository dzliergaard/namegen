import {Component, Inject} from "angular2/core";
import {GeneratedName, SavedName} from "name/name";
import {NameLine} from "name/name-line";
import {UserData} from "app/user-data";
import {Materials} from "util/materials";

@Component({
    selector: '[name-lists]',
    directives: [GeneratedName, SavedName, NameLine],
    templateUrl: 'templates/name/name-lists.component.html'
})
export class NameLists {
    private static generatedActions:any = {section: 'actions', main: 'primary-dark', text: 'primary-contrast'};
    private none:any = {};
    private cardClass:string;
    private generatedActionsClass:string;
    constructor(@Inject(UserData) private userData:UserData, @Inject(Materials) private materials:Materials) {
        this.cardClass = materials.cardClass({}, materials.cellClass({d: 5, t: 3, p: 4}));
        this.generatedActionsClass = materials.cardClass(NameLists.generatedActions, 'name-list mdl-list');
    }

    private savedNameOffsets() {
        return (!this.userData.generatedNames[0] ?
                'mdl-cell--6-offset-desktop mdl-cell--4-offset-tablet' :
                'mdl-cell--1-offset-desktop mdl-cell--1-offset-tablet');
    }
}