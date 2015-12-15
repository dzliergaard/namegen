import {bootstrap, Component, ElementRef, Input, OnInit, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import {NameList} from './name-list';
import {NameLearn} from './name-learn';
import {NameStore} from './name-store';
import {Name, Names} from './name';

@Component({
    selector: '[name-app]',
    templateUrl: 'templates/name.component.html',
    directives: [NameLearn, NameList, CORE_DIRECTIVES, FORM_DIRECTIVES]
})
export class NameComponent implements OnInit {
    toggle:boolean = false;
    saveLock:boolean = false;
    generating:boolean = false;
    userAuth:boolean = false;

    constructor(private nameStore: NameStore, private names: Names, private elementRef:ElementRef) {
    };

    onInit() {
        // init saved names
        const nativeElement = this.elementRef.nativeElement;
        var saved = nativeElement.getAttribute('saved-names') || '[]';
        var generated = nativeElement.getAttribute('generated-names') || '[]';
        var namesJson = JSON.parse(saved);
        var generatedJson = JSON.parse(generated);
        namesJson.forEach(item => this.saved.push(new Name(item.text, item.key)));
        generatedJson.forEach(item => this.generated.push(new Name(item.text, -1)));

        // init user auth
        this.userAuth = nativeElement.getAttribute('user-auth') || false;

        this.trainingName = JSON.parse(nativeElement.getAttribute('training-name'));
        this.nameAttributes = nativeElement.getAttribute('name-attributes').match(/(\w+)/g);
    }

    get generated () {
        return this.names.getGenerated();
    }

    get saved () {
        return this.names.getSaved();
    }

    generate (num: number) {
        if (num < 1) { return; }
        this.generating = true;
        this.nameStore.generate(num || 10).subscribe(res => {
            this.generating = false;
        });
    }

    removeName (name:Name) {
        return this.nameStore.remove(name);
    }

    saveName (name:Name) {
        return this.nameStore.save(name);
    }
}

bootstrap(NameComponent, [Names, NameStore]);