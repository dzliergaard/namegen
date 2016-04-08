"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_d_ts_1 = require("../node_modules/angular2/core.d.ts");
var common_d_ts_1 = require("../node_modules/angular2/common.d.ts");
var city_1 = require("./city");
var city_form_1 = require("./city-form");
var city_table_1 = require("./city-table");
var city_store_1 = require("./city-store");
var CityComponent = (function () {
    function CityComponent(cityStore, city, elementRef) {
        this.cityStore = cityStore;
        this.city = city;
        this.elementRef = elementRef;
        this.state = { generating: false };
        this.cityForm = new city_form_1.CityForm();
    }
    ;
    CityComponent.prototype.onInit = function () {
        var nativeElement = this.elementRef.nativeElement;
        this.diversityValues = nativeElement.getAttribute('diversity-values').match(/(\w+)/g);
        this.sizeValues = nativeElement.getAttribute('size-values').match(/(\w+)/g);
        this.speciesValues = nativeElement.getAttribute('species-values').match(/(\w+)/g);
    };
    CityComponent.prototype.generate = function (attr) {
        var _this = this;
        this.state.generating = true;
        this.cityStore.generate(this.cityForm.size, this.cityForm.race, this.cityForm.diversity, attr).subscribe(function (res) {
            _this.state.generating = false;
        });
    };
    CityComponent = __decorate([
        core_d_ts_1.Component({
            selector: '[city-app]',
            directives: [city_form_1.CityFormComponent, city_table_1.CityTable, common_d_ts_1.CORE_DIRECTIVES, common_d_ts_1.FORM_DIRECTIVES],
            template: "\n        <div class=\"row\">\n            <city-form (generate)=\"generate()\"\n                       [form]=\"cityForm\"\n                       [state]=\"state\"\n                       [diversity-values]=\"diversityValues\"\n                       [size-values]=\"sizeValues\"\n                       [species-values]=\"speciesValues\"></city-form>\n        </div>\n        <div class=\"row\" *ngIf=\"city.name\">\n            <city [city]=\"city\" class=\"col-xs-6\" (refresh-attr)=\"generate($event)\"></city>\n        </div>\n    "
        })
    ], CityComponent);
    return CityComponent;
}());
exports.CityComponent = CityComponent;
core_d_ts_1.bootstrap(CityComponent, [city_store_1.CityStore, core_d_ts_1.provide(city_1.City, { useValue: new city_1.City(null, null, null, null) })]);
//# sourceMappingURL=city-app.js.map