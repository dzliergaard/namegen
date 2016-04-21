import {Component, Inject, AfterViewInit, ElementRef} from "angular2/core";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {UserData} from "app/user-data";
import {NameStore} from "name/name-store";
import {Materials} from "util/materials";

@Component({
    selector: '[name-generate-form]',
    directives: [CORE_DIRECTIVES, FORM_DIRECTIVES],
    templateUrl: 'templates/name/name-generate-form.component.html'
})
export class NameGenerateForm implements AfterViewInit {
    private btnClass:string;
    constructor(@Inject(UserData) private userData:UserData,
                @Inject(NameStore) private nameStore:NameStore,
                @Inject(Materials) private materials:Materials,
                @Inject(ElementRef) private elementRef:ElementRef) {
        this.btnClass = materials.btnClass([], "mdl-cell");
    }

    ngAfterViewInit() {
        componentHandler.upgradeElements(this.elementRef.nativeElement.children);
    }

    generate (numNames?:number) {
        this.userData.generating = true;
        this.nameStore.generate(numNames).subscribe(res => {
            this.userData.generatedNames = res;
            this.userData.generating = false;
        });
    }
}