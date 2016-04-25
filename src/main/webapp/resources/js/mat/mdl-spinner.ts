import {AfterViewInit, Component, ElementRef, Inject, Input} from "angular2/core";
declare var componentHandler;

@Component({
    selector: 'mdl-spinner',
    template: `
        <div class="mdl-spinner mdl-spinner--single-color mdl-js-spinner" 
             [ngClass]="{'is-active': show, 'hidden': !show}"></div>
    `
})
export class MdlSpinner implements AfterViewInit {
    @Input('show') show:boolean;

    constructor(@Inject(ElementRef) private element:ElementRef) {
    }

    ngAfterViewInit() {
        componentHandler.upgradeElements(this.element.nativeElement);
    }
}
