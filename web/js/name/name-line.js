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
var input_strong_1 = require('../util/input-strong');
var NameLine = (function () {
    function NameLine() {
        this.primary = new angular2_1.EventEmitter();
        this.secondary = new angular2_1.EventEmitter();
    }
    NameLine.prototype.doSecondary = function (inputStrong) {
        inputStrong.doDone();
        this.secondary.next(name);
    };
    __decorate([
        angular2_1.Output(), 
        __metadata('design:type', Object)
    ], NameLine.prototype, "primary");
    __decorate([
        angular2_1.Output(), 
        __metadata('design:type', Object)
    ], NameLine.prototype, "secondary");
    NameLine = __decorate([
        angular2_1.Component({
            selector: 'name-line',
            properties: ['name', 'btnText', 'disabled'],
            template: "\n        <input-strong #inputStrong class=\"col-xs-8\" [item]=\"name\"></input-strong>\n        <button *ng-if=\"!inputstrong.editing\" class=\"btn col-xs-4\" (click)=\"primary.next(name)\" [disabled]=\"disabled\">{{btnText}}</button>\n        <button *ng-if=\"inputstrong.editing\" class=\"btn col-xs-4\" (click)=\"doSecondary(inputstrong)\">Done</button>\n    ",
            directives: [input_strong_1.InputStrong, angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [])
    ], NameLine);
    return NameLine;
})();
exports.NameLine = NameLine;
//# sourceMappingURL=name-line.js.map