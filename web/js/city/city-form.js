var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") return Reflect.decorate(decorators, target, key, desc);
    switch (arguments.length) {
        case 2: return decorators.reduceRight(function(o, d) { return (d && d(o)) || o; }, target);
        case 3: return decorators.reduceRight(function(o, d) { return (d && d(target, key)), void 0; }, void 0);
        case 4: return decorators.reduceRight(function(o, d) { return (d && d(target, key, o)) || o; }, desc);
    }
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var angular2_1 = require('angular2/angular2');
var form_radio_1 = require('../util/form-radio');
var CityForm = (function () {
    function CityForm(size, race, diversity) {
        this.size = size;
        this.race = race;
        this.diversity = diversity;
    }
    return CityForm;
})();
exports.CityForm = CityForm;
var CityFormComponent = (function () {
    function CityFormComponent() {
        this.generate = new angular2_1.EventEmitter();
    }
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', CityForm)
    ], CityFormComponent.prototype, "form");
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', Array)
    ], CityFormComponent.prototype, "diversityValues");
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', Array)
    ], CityFormComponent.prototype, "sizeValues");
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', Array)
    ], CityFormComponent.prototype, "speciesValues");
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', Object)
    ], CityFormComponent.prototype, "state");
    __decorate([
        angular2_1.Output(), 
        __metadata('design:type', Object)
    ], CityFormComponent.prototype, "generate");
    CityFormComponent = __decorate([
        angular2_1.Component({
            selector: 'city-form',
            directives: [form_radio_1.FormRadio, angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES],
            template: "\n        <form class='form-inline'>\n            <fieldset>\n                <legend>City Generator</legend>\n                <div class='row'>\n                    <div form-radio [form]=\"form\" [field-name]=\"'size'\" [values]=\"sizeValues\" [heading]=\"'Size'\"></div>\n                    <div form-radio [form]=\"form\" [field-name]=\"'race'\" [values]=\"speciesValues\" [heading]=\"'Dominant Species'\"></div>\n                    <div form-radio [form]=\"form\" [field-name]=\"'diversity'\" [values]=\"diversityValues\" [heading]=\"'Diversity'\"></div>\n                </div>\n                <div class=\"row\">\n                    <div class=\"col-xs-12\">\n                        <button class=\"btn generate-button\" (click)=\"generate.next()\">\n                            <span>Generate</span>\n                        </button>\n                        <img *ng-if=\"state.generating\" src=\"/resources/static/loading.gif\" height=\"20px\" width=\"20px\">\n                    </div>\n                </div>\n            </fieldset>\n        </form>\n    "
        }), 
        __metadata('design:paramtypes', [])
    ], CityFormComponent);
    return CityFormComponent;
})();
exports.CityFormComponent = CityFormComponent;
//# sourceMappingURL=city-form.js.map