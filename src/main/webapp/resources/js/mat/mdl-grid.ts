import {Input, Inject, ElementRef, Directive, AfterViewChecked} from "angular2/core";
import _ = require("underscore");

@Directive({
    selector: '[cell]'
})
export class MdlCell implements AfterViewChecked {
    private static cellClassRegex:RegExp = /\s?mdl-cell--\d-(?:col|offset)(?:-\w+)?/g;
    @Input('cell') private baseCols:number;
    @Input('d') private desktopCols:number;
    @Input('t') private tabletCols:number;
    @Input('p') private phoneCols:number;
    @Input('o') private offset:number;
    @Input('d-o') private desktopOffset:number;
    @Input('t-o') private tabletOffset:number;
    @Input('p-o') private phoneOffset:number;

    constructor(@Inject(ElementRef) private element:ElementRef) {
        element.nativeElement.classList.add("mdl-cell");
    }

    ngAfterViewChecked() {
        // remove current cell classes
        this.element.nativeElement.className = this.element.nativeElement.className.replace(MdlCell.cellClassRegex, "");
        if (this.baseCols) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.baseCols + "-col");
        }
        if (this.desktopCols) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.desktopCols + "-col-desktop");
        }
        if (this.tabletCols) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.tabletCols + "-col-tablet");
        }
        if (this.phoneCols) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.phoneCols + "-col-phone");
        }

        // add offset width classes
        if (this.offset) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.offset + "-offset");
        }
        if (this.desktopOffset) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.desktopOffset + "-offset-desktop");
        }
        if (this.tabletOffset) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.tabletOffset + "-offset-tablet");
        }
        if (this.phoneOffset) {
            this.element.nativeElement.classList.add("mdl-cell--" + this.phoneOffset + "-offset-phone");
        }
    }
}

@Directive({
    selector: '[mdl-grid]'
})
export class MdlGrid {
    constructor(@Inject(ElementRef) private element:ElementRef) {
        element.nativeElement.className = (element.nativeElement.className + " mdl-grid").trim();
    }
}