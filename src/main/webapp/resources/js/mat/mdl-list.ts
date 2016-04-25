import {Directive, Input, Inject, ElementRef, AfterContentInit, Host, AfterViewInit} from "angular2/core";
import _ = require("underscore");

@Directive({
    selector: '[mdl-list]'
})
export class MdlList implements AfterViewInit {
    @Input('item-class') private itemClass:string;
    @Input('primary-class') private primaryClass:string;
    @Input('secondary-class') private secondaryActionClass:string;

    constructor(@Inject(ElementRef) private element:ElementRef) {
        element.nativeElement.className = (element.nativeElement.className + " mdl-list").trim();
    }

    addItemClass(element:ElementRef) {
        this.addChildClass(element.nativeElement, 'mdl-list__item', this.itemClass);
    }

    addPrimaryClass(element:any) {
        this.addChildClass(element, 'mdl-list__item-primary-content', this.primaryClass);
    }

    addSecondaryActionClass(element:any) {
        this.addChildClass(element, 'mdl-list__item-secondary-action', this.secondaryActionClass);
    }

    private addChildClass(element:any, mdlClass:string, itemClass:string) {
        let classNames = ["", mdlClass];
        if (itemClass) {
            classNames.push(itemClass);
        }

        element.className += classNames.join(" ");
    }
}

@Directive({
    selector: 'li'
})
export class MdlListItem implements AfterContentInit, AfterViewInit {
    constructor(@Inject(ElementRef) public element:ElementRef, @Host() @Inject(MdlList) private list:MdlList) {
    }

    ngAfterContentInit() {
        this.list.addItemClass(this.element);
    }

    ngAfterViewInit() {
        let primary = _.find(this.element.nativeElement.children, item => !!item.attributes['primary']);
        let secondary = _.find(this.element.nativeElement.children, item => !!item.attributes['secondary']);
        if (primary) {
            this.list.addPrimaryClass(primary);
        }
        if (secondary) {
            this.list.addSecondaryActionClass(secondary);
        }
    }
}