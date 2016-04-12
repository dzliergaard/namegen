"use strict";
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require('angular2/core');
var entity_store_1 = require('../util/entity-store');
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
        core_1.Injectable()
    ], CityStore);
    return CityStore;
}(entity_store_1.EntityStore));
exports.CityStore = CityStore;
//# sourceMappingURL=city-store.js.map