System.register(["angular2/core", "angular2/common", "mat/materials", "name/name", "util/input-strong"], function(exports_1, context_1) {
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
    var core_1, common_1, materials_1, name_1, input_strong_1;
    var NameLine;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
            },
            function (materials_1_1) {
                materials_1 = materials_1_1;
            },
            function (name_1_1) {
                name_1 = name_1_1;
            },
            function (input_strong_1_1) {
                input_strong_1 = input_strong_1_1;
            }],
        execute: function() {
            NameLine = (function () {
                function NameLine(generatedName, savedName) {
                    this.savedButton = false;
                    this.name = generatedName ? generatedName : savedName;
                    this.savedButton = !!savedName;
                }
                NameLine.prototype.btnClick = function ($event) {
                    if ($event.button !== 0) {
                        return;
                    }
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
                        directives: [input_strong_1.InputStrong, common_1.CORE_DIRECTIVES, materials_1.MDL_COMPONENTS],
                        template: "\n        <span input-strong primary class=\"mdl-cell--stretch\" [(item)]=name (done)=name.doSecondary(name)></span>\n        <button secondary cell=2 [mdl-btn]=\"savedButton ? 'primary': ''\" [flat]=\"savedButton && name.isSaving()\" \n                (mousedown)=btnClick($event) [disabled]=name.btnDisabled()>\n            <span >{{btnContent()}}</span>\n            <mdl-spinner [show]=name.isSaving()></mdl-spinner>\n        </button>\n    "
                    }),
                    __param(0, core_1.Inject(name_1.GeneratedName)),
                    __param(0, core_1.Optional()),
                    __param(1, core_1.Inject(name_1.SavedName)),
                    __param(1, core_1.Optional()), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof name_1.GeneratedName !== 'undefined' && name_1.GeneratedName) === 'function' && _a) || Object, (typeof (_b = typeof name_1.SavedName !== 'undefined' && name_1.SavedName) === 'function' && _b) || Object])
                ], NameLine);
                return NameLine;
                var _a, _b;
            }());
            exports_1("NameLine", NameLine);
        }
    }
});
//# sourceMappingURL=name-line.js.map