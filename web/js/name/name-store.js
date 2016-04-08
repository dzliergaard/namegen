"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
var core_1 = require("angular2/core");
var name_calls_1 = require("name/name-calls");
var NameStore = (function () {
    function NameStore(caller) {
        this.caller = caller;
    }
    NameStore.prototype.generate = function (num) {
        return this.caller.generate(num);
    };
    NameStore.prototype.save = function (name) {
        return this.caller.save(name);
    };
    NameStore.prototype.remove = function (name) {
        return this.caller.remove(name);
    };
    NameStore.prototype.train = function (name) {
        return this.caller.train(name);
    };
    NameStore = __decorate([
        core_1.Injectable(),
        __param(0, core_1.Inject(name_calls_1.NameCalls))
    ], NameStore);
    return NameStore;
}());
exports.NameStore = NameStore;
//# sourceMappingURL=name-store.js.map