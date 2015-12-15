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
var name_1 = require('./name');
var InitNames = (function () {
    function InitNames(names) {
        this.names = names;
    }
    Object.defineProperty(InitNames.prototype, "saved", {
        get: function () {
            return this.names.getSaved();
        },
        enumerable: true,
        configurable: true
    });
    InitNames.prototype.parseSaved = function () {
        var _this = this;
        var namesJson = JSON.parse(names);
        var nameMap = namesJson.map(function (item) { return new name_1.Name(item.text, item.key); });
        nameMap.forEach(function (item) { return _this.saved.push(item); });
    };
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', String)
    ], InitNames.prototype, "savedNames");
    InitNames = __decorate([
        angular2_1.Directive({
            selector: 'init-names'
        }), 
        __metadata('design:paramtypes', [name_1.Names])
    ], InitNames);
    return InitNames;
})();
exports.InitNames = InitNames;
//# sourceMappingURL=init-names.js.map