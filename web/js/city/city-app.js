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
var city_1 = require('./city');
var city_form_1 = require('./city-form');
var city_table_1 = require('./city-table');
var city_store_1 = require('./city-store');
var CityComponent = (function () {
    function CityComponent(cityStore, city, elementRef) {
        this.cityStore = cityStore;
        this.city = city;
        this.elementRef = elementRef;
        this.state = { generating: false };
        this.cityForm = new city_form_1.CityForm();
    }
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
        angular2_1.Component({
            selector: '[city-app]',
            directives: [city_form_1.CityFormComponent, city_table_1.CityTable, angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES],
            template: "\n        <div class=\"row\">\n            <city-form (generate)=\"generate()\"\n                       [form]=\"cityForm\"\n                       [state]=\"state\"\n                       [diversity-values]=\"diversityValues\"\n                       [size-values]=\"sizeValues\"\n                       [species-values]=\"speciesValues\"></city-form>\n        </div>\n        <div class=\"row\" *ng-if=\"city.name\">\n            <city [city]=\"city\" class=\"col-xs-6\" (refresh-attr)=\"generate($event)\"></city>\n        </div>\n    "
        }), 
        __metadata('design:paramtypes', [city_store_1.CityStore, city_1.City, angular2_1.ElementRef])
    ], CityComponent);
    return CityComponent;
})();
exports.CityComponent = CityComponent;
angular2_1.bootstrap(CityComponent, [city_store_1.CityStore, angular2_1.provide(city_1.City, { useValue: new city_1.City(null, null, null, null) })]);
//# sourceMappingURL=city-app.js.map