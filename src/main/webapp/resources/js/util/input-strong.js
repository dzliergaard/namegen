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
var common_1 = require("angular2/common");
var $ = require("jquery");
var InputStrongData = (function () {
    function InputStrongData() {
        this.editing = false;
        this.saving = false;
    }
    InputStrongData.prototype.edit = function () {
        this.editing = true;
        this.saving = false;
    };
    InputStrongData.prototype.isEditing = function () {
        return this.editing;
    };
    InputStrongData.prototype.doneEditing = function () {
        this.editing = false;
    };
    InputStrongData.prototype.save = function () {
        this.editing = false;
        this.saving = true;
    };
    InputStrongData.prototype.isSaving = function () {
        return this.saving;
    };
    InputStrongData.prototype.doneSaving = function () {
        this.saving = false;
    };
    return InputStrongData;
}());
exports.InputStrongData = InputStrongData;
var InputStrong = (function () {
    function InputStrong(elementRef) {
        this.elementRef = elementRef;
        this.select = false;
    }
    InputStrong.prototype.ngAfterViewChecked = function () {
        if (this.select) {
            $(this.elementRef.nativeElement).find('input').select();
            this.select = false;
        }
    };
    InputStrong.prototype.doDone = function () {
        this.select = false;
        if (this.item.isEditing()) {
            this.item.doneEditing();
        }
    };
    __decorate([
        core_1.Input()
    ], InputStrong.prototype, "item");
    InputStrong = __decorate([
        core_1.Component({
            selector: 'input-strong',
            directives: [common_1.FORM_DIRECTIVES],
            template: "\n        <strong class=\"col-xs-12\" [hidden]=\"item.isEditing()\" (click)=\"select = true; item.edit()\">\n            {{item.text}}\n        </strong>\n        <input #input class=\"col-xs-12\" [hidden]=\"!item.isEditing()\" [(ngModel)]=\"item.text\" (keyup.enter)=\"doDone()\" (blur)=\"doDone()\"/>\n    "
        }),
        __param(0, core_1.Inject(core_1.ElementRef))
    ], InputStrong);
    return InputStrong;
}());
exports.InputStrong = InputStrong;
//# sourceMappingURL=input-strong.js.map