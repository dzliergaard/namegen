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
var $ = require('jquery');
var InputStrong = (function () {
    function InputStrong() {
        this.edit = new angular2_1.EventEmitter();
        this.done = new angular2_1.EventEmitter();
        this.select = true;
        this.editing = false;
    }
    InputStrong.prototype.afterViewChecked = function () {
        if (!this.input || !this.select) {
            return;
        }
        $(this.input).select();
        this.select = false;
    };
    InputStrong.prototype.doEdit = function (input) {
        this.input = input;
        this.editing = true;
        this.select = true;
        this.edit.next(this.item);
    };
    InputStrong.prototype.doDone = function () {
        this.editing = false;
        this.done.next(this.item);
    };
    __decorate([
        angular2_1.Input(), 
        __metadata('design:type', Object)
    ], InputStrong.prototype, "item");
    InputStrong = __decorate([
        angular2_1.Component({
            selector: 'input-strong',
            outputs: ['edit', 'done'],
            directives: [angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES],
            template: "\n        <strong class=\"col-xs-12\" [hidden]=\"editing\" (click)=\"doEdit(input)\">{{item.text}}</strong>\n        <input #input [hidden]=\"!editing\" class=\"col-xs-12\" (keyup.enter)=\"doDone()\" [(ng-model)]=\"item.text\" (blur)=\"doDone()\"/>\n    ",
            exportAs: 'inputstrong'
        }), 
        __metadata('design:paramtypes', [])
    ], InputStrong);
    return InputStrong;
})();
exports.InputStrong = InputStrong;
//# sourceMappingURL=input-strong.js.map