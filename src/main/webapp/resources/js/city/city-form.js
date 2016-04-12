"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_d_ts_1 = require('../node_modules/angular2/core.d.ts');
var common_d_ts_1 = require('../node_modules/angular2/common.d.ts');
var form_radio_1 = require('../util/form-radio');
var CityForm = (function () {
    function CityForm(size, race, diversity) {
        this.size = size;
        this.race = race;
        this.diversity = diversity;
    }
    return CityForm;
}());
exports.CityForm = CityForm;
var CityFormComponent = (function () {
    function CityFormComponent() {
        this.generate = new core_d_ts_1.EventEmitter();
    }
    __decorate([
        core_d_ts_1.Input()
    ], CityFormComponent.prototype, "form");
    __decorate([
        core_d_ts_1.Input()
    ], CityFormComponent.prototype, "diversityValues");
    __decorate([
        core_d_ts_1.Input()
    ], CityFormComponent.prototype, "sizeValues");
    __decorate([
        core_d_ts_1.Input()
    ], CityFormComponent.prototype, "speciesValues");
    __decorate([
        core_d_ts_1.Input()
    ], CityFormComponent.prototype, "state");
    __decorate([
        core_d_ts_1.Output()
    ], CityFormComponent.prototype, "generate");
    CityFormComponent = __decorate([
        core_d_ts_1.Component({
            selector: 'city-form',
            directives: [form_radio_1.FormRadio, common_d_ts_1.CORE_DIRECTIVES, common_d_ts_1.FORM_DIRECTIVES],
            template: "\n        <form class='form-inline'>\n            <fieldset>\n                <legend>City Generator</legend>\n                <div class='row'>\n                    <div form-radio [form]=\"form\" [field-name]=\"'size'\" [values]=\"sizeValues\" [heading]=\"'Size'\"></div>\n                    <div form-radio [form]=\"form\" [field-name]=\"'race'\" [values]=\"speciesValues\" [heading]=\"'Dominant Species'\"></div>\n                    <div form-radio [form]=\"form\" [field-name]=\"'diversity'\" [values]=\"diversityValues\" [heading]=\"'Diversity'\"></div>\n                </div>\n                <div class=\"row\">\n                    <div class=\"col-xs-12\">\n                        <button class=\"btn generate-button\" (click)=\"generate.next()\">\n                            <span>Generate</span>\n                        </button>\n                        <img *ngIf=\"state.generating\" src=\"/resources/static/loading.gif\" height=\"20px\" width=\"20px\">\n                    </div>\n                </div>\n            </fieldset>\n        </form>\n    "
        })
    ], CityFormComponent);
    return CityFormComponent;
}());
exports.CityFormComponent = CityFormComponent;
//# sourceMappingURL=city-form.js.map