import {AfterViewInit, Component, Directive, ElementRef, Inject, Input, Host} from "angular2/core";

@Directive({
    selector: '[mdl-card]'
})
export class MdlCard implements AfterViewInit {
    @Input('mdl-card') private backgroundType:string;
    @Input('content-color') public contentColor:string;
    public contentTextClass:string = 'mdl-color-text--';

    constructor(@Inject(ElementRef) private element:ElementRef) {
    }

    ngAfterViewInit() {
        this.element.nativeElement.classList.add('mdl-card');
        switch(this.backgroundType) {
            case 'primary':
            default:
                this.element.nativeElement.classList.add('mdl-card', 'mdl-color--primary', 'mdl-color-text--primary-contrast');
                break;
            case 'accent':
                this.element.nativeElement.classList.add('mdl-card', 'mdl-color--accent', 'mdl-color-text--accent-contrast');
                break;
            case 'dark':
            case 'primary-dark':
                this.element.nativeElement.classList.add('mdl-card', 'mdl-color--primary-dark', 'mdl-color-text--accent-contrast');
                break;
        }
    }

    get colorClass() {
        switch(this.contentColor) {
            case 'primary':
                return 'mdl-color--primary';
            case 'accent':
            default:
                return 'mdl-color--accent';
            case 'dark':
            case 'primary-dark':
                return 'mdl-color--primary-dark';
        }
    }

    get textClass() {
        switch(this.contentColor) {
            case 'primary':
            case 'dark':
            case 'primary-dark':
                return 'mdl-color-text--primary-contrast';
            case 'accent':
            default:
                return 'mdl-color-text--accent-contrast';
        }
    }
}

@Component({
    selector: '[card-title]',
    template: `<h2 class="mdl-card__title-text"><ng-content></ng-content></h2>`
})
export class MdlCardTitle implements AfterViewInit {
    constructor(@Inject(ElementRef) private element:ElementRef, @Host() @Inject(MdlCard) private card:MdlCard) {
    }

    ngAfterViewInit() {
        this.element.nativeElement.classList.add('mdl-card__title', this.card.colorClass, this.card.textClass);
    }
}

@Component({
    selector: '[card-actions]',
    template: `<ng-content></ng-content>`
})
export class MdlCardActions implements AfterViewInit {
    constructor(@Inject(ElementRef) private element:ElementRef, @Host() @Inject(MdlCard) private card:MdlCard) {
    }

    ngAfterViewInit() {
        this.element.nativeElement.classList.add('mdl-card__actions', this.card.colorClass, this.card.textClass);
    }
}