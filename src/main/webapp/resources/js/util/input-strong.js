System.register(["angular2/core", "angular2/common"], function(exports_1, context_1) {
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
    var core_1, common_1;
    var InputStrongData, InputElement, InputStrong;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
            }],
        execute: function() {
            InputStrongData = (function () {
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
            exports_1("InputStrongData", InputStrongData);
            InputElement = (function () {
                function InputElement(elementRef) {
                    this.elementRef = elementRef;
                }
                InputElement = __decorate([
                    core_1.Directive({
                        selector: 'input'
                    }),
                    __param(0, core_1.Inject(core_1.ElementRef)), 
                    __metadata('design:paramtypes', [core_1.ElementRef])
                ], InputElement);
                return InputElement;
            }());
            InputStrong = (function () {
                function InputStrong() {
                    this.select = false;
                }
                InputStrong.prototype.ngAfterViewChecked = function () {
                    if (this.select) {
                        this.input.elementRef.nativeElement.select();
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
                    core_1.Input(), 
                    __metadata('design:type', InputStrongData)
                ], InputStrong.prototype, "item", void 0);
                __decorate([
                    core_1.ViewChild(InputElement), 
                    __metadata('design:type', InputElement)
                ], InputStrong.prototype, "input", void 0);
                InputStrong = __decorate([
                    core_1.Component({
                        selector: '[input-strong]',
                        directives: [InputElement, common_1.FORM_DIRECTIVES],
                        template: "\n        <strong class=\"mdl-cell mdl-cell--12-col\" [hidden]=\"item.isEditing()\" (click)=\"select = true; item.edit()\">\n            {{item.text}}\n        </strong>\n        <input class=\"mdl-cell mdl-cell--12-col mdl-textfield__input mdl-textfield--expandable\" [hidden]=\"!item.isEditing()\" [(ngModel)]=\"item.text\" (keyup.enter)=\"doDone(input)\" (blur)=\"doDone()\"/>\n    "
                    }), 
                    __metadata('design:paramtypes', [])
                ], InputStrong);
                return InputStrong;
            }());
            exports_1("InputStrong", InputStrong);
        }
    }
});
//# sourceMappingURL=input-strong.js.map