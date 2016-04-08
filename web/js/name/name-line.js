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
var name_1 = require("name/name");
var input_strong_1 = require("util/input-strong");
var NameLine = (function () {
    function NameLine(generatedName, savedName) {
        if (generatedName) {
            this.name = generatedName;
        }
        else if (savedName) {
            this.name = savedName;
        }
    }
    NameLine.prototype.btnClick = function () {
        return this.name.isEditing() ? this.name.doSecondary() : this.name.doPrimary();
    };
    NameLine.prototype.btnContent = function () {
        if (this.name.isSaving()) {
            return "";
        }
        else if (!this.name.isEditing()) {
            return this.name.btnText();
        }
        else {
            return "Done";
        }
    };
    NameLine = __decorate([
        core_1.Component({
            selector: '[dz-name-line]',
            directives: [input_strong_1.InputStrong, common_1.CORE_DIRECTIVES],
            template: "\n        <input-strong class=\"col-xs-8\" [(item)]=\"name\" (done)=\"name.doSecondary(name)\"></input-strong>\n        <button class=\"btn col-xs-4\" (mousedown)=\"btnClick()\" [disabled]=\"name.btnDisabled()\">\n            {{btnContent()}}\n            <img class=\"loading\" *ngIf=\"name.isSaving()\" src=\"resources/static/loading.gif\" height=\"30px\" width=\"30px\">\n        </button>\n    "
        }),
        __param(0, core_1.Inject(name_1.GeneratedName)),
        __param(0, core_1.Optional()),
        __param(1, core_1.Inject(name_1.SavedName)),
        __param(1, core_1.Optional())
    ], NameLine);
    return NameLine;
}());
exports.NameLine = NameLine;
//# sourceMappingURL=name-line.js.map