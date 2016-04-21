System.register(["angular2/core", "name/name-calls"], function(exports_1, context_1) {
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
    var core_1, name_calls_1;
    var NameStore;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (name_calls_1_1) {
                name_calls_1 = name_calls_1_1;
            }],
        execute: function() {
            NameStore = (function () {
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
                NameStore.prototype.attributes = function () {
                    return this.caller.attributes();
                };
                NameStore = __decorate([
                    core_1.Injectable(),
                    __param(0, core_1.Inject(name_calls_1.NameCalls)), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof name_calls_1.NameCalls !== 'undefined' && name_calls_1.NameCalls) === 'function' && _a) || Object])
                ], NameStore);
                return NameStore;
                var _a;
            }());
            exports_1("NameStore", NameStore);
        }
    }
});
//# sourceMappingURL=name-store.js.map