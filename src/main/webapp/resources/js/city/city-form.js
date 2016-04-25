System.register(["angular2/core", "angular2/common", "app/user-data", "city/city-store", "mat/materials", "util/form-radio"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var __param = (this && this.__param) || function (paramIndex, decorator) {
        return function (target, key) { decorator(target, key, paramIndex); }
    };
    var core_1, common_1, user_data_1, city_store_1, materials_1, form_radio_1;
    var CityForm;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
            },
            function (user_data_1_1) {
                user_data_1 = user_data_1_1;
            },
            function (city_store_1_1) {
                city_store_1 = city_store_1_1;
            },
            function (materials_1_1) {
                materials_1 = materials_1_1;
            },
            function (form_radio_1_1) {
                form_radio_1 = form_radio_1_1;
            }],
        execute: function() {
            CityForm = (function () {
                function CityForm(cityStore, userData) {
                    var _this = this;
                    this.cityStore = cityStore;
                    this.userData = userData;
                    this.diversityData = {
                        heading: 'Diversity',
                        value: ''
                    };
                    this.sizeData = {
                        heading: 'Size',
                        value: ''
                    };
                    this.speciesData = {
                        heading: 'Dominant Species',
                        value: ''
                    };
                    cityStore.variables().subscribe(function (res) {
                        _this.diversityData.values = res.diversityValues;
                        _this.sizeData.values = res.sizeValues;
                        _this.speciesData.values = res.speciesValues;
                    });
                }
                CityForm.prototype.generate = function () {
                    var _this = this;
                    this.userData.generating = true;
                    this.cityStore.generate(this.sizeData.value, this.speciesData.value, this.diversityData.value).subscribe(function (res) {
                        _this.userData.generatedCity = res;
                        _this.userData.generating = false;
                    });
                };
                CityForm = __decorate([
                    core_1.Component({
                        selector: '.city-form',
                        directives: [form_radio_1.FormRadio, common_1.CORE_DIRECTIVES, common_1.FORM_DIRECTIVES, materials_1.MDL_COMPONENTS],
                        templateUrl: 'templates/city/city-form.component.html'
                    }),
                    __param(0, core_1.Inject(city_store_1.CityStore)),
                    __param(1, core_1.Inject(user_data_1.UserData)), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof city_store_1.CityStore !== 'undefined' && city_store_1.CityStore) === 'function' && _a) || Object, (typeof (_b = typeof user_data_1.UserData !== 'undefined' && user_data_1.UserData) === 'function' && _b) || Object])
                ], CityForm);
                return CityForm;
                var _a, _b;
            }());
            exports_1("CityForm", CityForm);
        }
    }
});
//# sourceMappingURL=city-form.js.map