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
var name_line_1 = require('./name-line');
var NameList = (function () {
    function NameList() {
        this.primary = new angular2_1.EventEmitter();
        this.secondary = new angular2_1.EventEmitter();
    }
    __decorate([
        angular2_1.Output(), 
        __metadata('design:type', Object)
    ], NameList.prototype, "primary");
    __decorate([
        angular2_1.Output(), 
        __metadata('design:type', Object)
    ], NameList.prototype, "secondary");
    NameList = __decorate([
        angular2_1.Component({
            selector: 'name-list',
            properties: ['names', 'btnText', 'disabled'],
            template: "\n        <name-line *ng-for=\"#name of names\"\n                   class=\"col-xs-12\"\n                   [name]=\"name\"\n                   [btn-text]=\"btnText\"\n                   [disabled]=\"disabled\"\n                   (primary)=\"primary.next($event)\"\n                   (secondary)=\"secondary.next($event)\"/>\n    ",
            directives: [name_line_1.NameLine, angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [])
    ], NameList);
    return NameList;
})();
exports.NameList = NameList;
//# sourceMappingURL=name-list.js.map