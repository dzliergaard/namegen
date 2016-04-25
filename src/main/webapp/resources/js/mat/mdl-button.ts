import {Input, Inject, ElementRef, AfterViewChecked, Directive, AfterViewInit} from "angular2/core";
declare var componentHandler;

@Directive({
    selector: 'button[mdl-btn]'
})
export class MdlButton implements AfterViewInit, AfterViewChecked {
    private static buttonTypesRegex:RegExp = /\s?mdl-button--(?:primary|accent|raised)/g;
    @Input('mdl-btn') type:string;
    @Input('flat') flat:boolean;

    constructor(@Inject(ElementRef) private element:ElementRef) {
        this.element.nativeElement.classList.add("mdl-button", "mdl-js-button", "mdl-js-ripple-effect");
    }

    ngAfterViewInit() {
        componentHandler.upgradeElements(this.element.nativeElement);
    }

    ngAfterViewChecked() {
        this.element.nativeElement.className = this.element.nativeElement.className.replace(MdlButton.buttonTypesRegex, "");
        if (!this.flat) {
            this.element.nativeElement.classList.add('mdl-button--raised');
        }
        if (this.type == 'primary') {
            this.element.nativeElement.classList.add('mdl-button--primary');
        } else {
            this.element.nativeElement.classList.add('mdl-button--accent');
        }
    }
}
