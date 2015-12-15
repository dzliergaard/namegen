var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
var entity_store_1 = require('../util/entity-store');
var city_1 = require('./city');
var CityStore = (function (_super) {
    __extends(CityStore, _super);
    function CityStore(city) {
        _super.call(this, "city");
        this.city = city;
    }
    CityStore.prototype.updateCity = function () {
        this.updateAttr('name');
        this.updateAttr('ruler');
        this.updateAttr('population');
        this.updateAttr('inns');
    };
    CityStore.prototype.updateAttr = function (attr) {
        this.city[attr] = this.newCity[attr];
    };
    CityStore.prototype.generate = function (size, race, diversity, attr) {
        var _this = this;
        var answer = _super.prototype.generate.call(this, {
            size: size,
            race: race,
            diversity: diversity
        });
        answer.subscribe(function (res) {
            _this.generating = false;
            _this.newCity = res.json();
            if (attr) {
                _this.updateAttr(attr);
            }
            else {
                _this.updateCity();
            }
        });
        return answer;
    };
    CityStore = __decorate([
        angular2_1.Injectable(), 
        __metadata('design:paramtypes', [city_1.City])
    ], CityStore);
    return CityStore;
})(entity_store_1.EntityStore);
exports.CityStore = CityStore;
//# sourceMappingURL=city-store.js.map