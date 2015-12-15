import {Component, Input, CORE_DIRECTIVES} from 'angular2/angular2';

@Component({
    selector: '[form-radio]',
    directives: [CORE_DIRECTIVES],
    template: `
        <div class='col-xs-3 panel-group' id='accordian' role='tablist' aria-multiselectable='true'>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab">
                    <h4 class="panel-title">
                        <a data-toggle="collapse"
                           data-parent="#accordion"
                           aria-expanded="true"
                           [attr.href]="'#collapse' + heading"
                           [attr.aria-controls]="'collapse' + heading">
                            {{heading}}
                        </a>
                    </h4>
                </div>
                <div role="tabpanel" [attr.aria-labelledby]="'heading' + heading">
                    <div class="panel-body">
                        <div class='btn-group-vertical'>
                            <label class='btn btn-default' [class.active]="!form[fieldName]">
                                <input type='radio' (click)="form[fieldName] = ''" [value]="">Random
                            </label>
                            <label *ng-for="#item of values" class="btn btn-default" [class.active]="form[fieldName] == item">
                                <input type='radio' (click)="form[fieldName] = item">{{item}}
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
})
export class FormRadio {
    @Input() private fieldName:string;
    @Input() private values:Array<string>;
    @Input() private heading:string;
    @Input() private form:any;
}