System.register(["angular2/core", "app/user-data", "name/name-store", "util/input-strong", "underscore"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
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
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var __param = (this && this.__param) || function (paramIndex, decorator) {
        return function (target, key) { decorator(target, key, paramIndex); }
    };
    var core_1, user_data_1, name_store_1, input_strong_1, _;
    var Name, GeneratedName, SavedName;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (user_data_1_1) {
                user_data_1 = user_data_1_1;
            },
            function (name_store_1_1) {
                name_store_1 = name_store_1_1;
            },
            function (input_strong_1_1) {
                input_strong_1 = input_strong_1_1;
            },
            function (_1) {
                _ = _1;
            }],
        execute: function() {
            /**
             * Base class represents the *concept* of a name, not an actual URI element.
             */
            Name = (function (_super) {
                __extends(Name, _super);
                function Name(nameStore) {
                    _super.call(this);
                    this.nameStore = nameStore;
                }
                Name.prototype.saveName = function () {
                    var _this = this;
                    this.save();
                    this.nameStore.save(this.nameData).subscribe(function (n) { return _this.saveCallback(n); }, function (e) { return _this.saveError(e); });
                };
                Name.prototype.saveCallback = function (response) {
                    this.doneSaving();
                    this.text = response.text;
                    this.lastSavedText = response.text;
                };
                Name.prototype.saveError = function (error) {
                    console.log("Error attempting to save name: " + this.text + "\n" + error);
                };
                Name.prototype.doPrimary = function () {
                };
                Name.prototype.doSecondary = function () {
                };
                Name.prototype.btnText = function () {
                    return "";
                };
                Name.prototype.btnDisabled = function () {
                    return false;
                };
                Object.defineProperty(Name.prototype, "nameData", {
                    get: function () {
                        return {
                            id: this.id,
                            text: this.text
                        };
                    },
                    set: function (nameData) {
                        this.id = nameData.id;
                        this.text = nameData.text;
                        this.lastSavedText = nameData.text;
                    },
                    enumerable: true,
                    configurable: true
                });
                Name = __decorate([
                    __param(0, core_1.Inject(name_store_1.NameStore)), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof name_store_1.NameStore !== 'undefined' && name_store_1.NameStore) === 'function' && _a) || Object])
                ], Name);
                return Name;
                var _a;
            }(input_strong_1.InputStrongData));
            exports_1("Name", Name);
            GeneratedName = (function (_super) {
                __extends(GeneratedName, _super);
                function GeneratedName(nameStore, userData) {
                    _super.call(this, nameStore);
                    this.nameStore = nameStore;
                    this.userData = userData;
                }
                GeneratedName.prototype.saveCallback = function (response) {
                    _super.prototype.saveCallback.call(this, response);
                    this.userData.savedNames.push(response);
                    console.log("New name saved: " + JSON.stringify(response));
                };
                GeneratedName.prototype.doPrimary = function () {
                    return this.saveName();
                };
                GeneratedName.prototype.btnText = function () {
                    return this.isEditing() ? "Done" : "Save";
                };
                GeneratedName.prototype.btnDisabled = function () {
                    return !this.isEditing() && !this.userData.isSignedIn;
                };
                GeneratedName = __decorate([
                    core_1.Directive({
                        selector: '[dz-generated-name]',
                        inputs: ['nameData: dz-generated-name']
                    }),
                    __param(0, core_1.Inject(name_store_1.NameStore)),
                    __param(1, core_1.Inject(user_data_1.UserData)), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof name_store_1.NameStore !== 'undefined' && name_store_1.NameStore) === 'function' && _a) || Object, (typeof (_b = typeof user_data_1.UserData !== 'undefined' && user_data_1.UserData) === 'function' && _b) || Object])
                ], GeneratedName);
                return GeneratedName;
                var _a, _b;
            }(Name));
            exports_1("GeneratedName", GeneratedName);
            SavedName = (function (_super) {
                __extends(SavedName, _super);
                function SavedName(nameStore, userData) {
                    _super.call(this, nameStore);
                    this.userData = userData;
                }
                SavedName.prototype.saveCallback = function (response) {
                    _super.prototype.saveCallback.call(this, response);
                    this.id = response.id;
                    console.log("Name updated: " + JSON.stringify(response));
                };
                SavedName.prototype.doneEditing = function () {
                    _super.prototype.doneEditing.call(this);
                    this.doSecondary();
                };
                SavedName.prototype.doPrimary = function () {
                    var _this = this;
                    this.save();
                    return this.nameStore.remove(this.nameData).subscribe(function () {
                        _this.userData.savedNames = _.reject(_this.userData.savedNames, function (n) { return n.id == _this.id; });
                    }, function (err) { return _this.doneSaving(); });
                };
                SavedName.prototype.doSecondary = function () {
                    if (this.text != this.lastSavedText) {
                        this.saveName();
                    }
                };
                SavedName.prototype.btnText = function () {
                    return this.isEditing() ? "Done" : "Remove";
                };
                SavedName = __decorate([
                    core_1.Directive({
                        selector: '[dz-saved-name]',
                        inputs: ['nameData: dz-saved-name']
                    }),
                    __param(0, core_1.Inject(name_store_1.NameStore)),
                    __param(1, core_1.Inject(user_data_1.UserData)), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof name_store_1.NameStore !== 'undefined' && name_store_1.NameStore) === 'function' && _a) || Object, (typeof (_b = typeof user_data_1.UserData !== 'undefined' && user_data_1.UserData) === 'function' && _b) || Object])
                ], SavedName);
                return SavedName;
                var _a, _b;
            }(Name));
            exports_1("SavedName", SavedName);
        }
    }
});
//# sourceMappingURL=name.js.map