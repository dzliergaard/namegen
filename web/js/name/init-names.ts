import {bootstrap, Directive, Input} from 'angular2/angular2';
import {Name, Names} from './name';

@Directive({
    selector: 'init-names'
})
export class InitNames {
    @Input() public savedNames:string;
    constructor(private names:Names){
    }

    get saved () {
        return this.names.getSaved();
    }

    parseSaved() {
        var namesJson = JSON.parse(names);
        var nameMap = namesJson.map(item => new Name(item.text, item.key));
        nameMap.forEach(item => this.saved.push(item));
    }
}