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
var name_list_1 = require('./name-list');
var name_learn_1 = require('./name-learn');
var name_store_1 = require('./name-store');
var name_1 = require('./name');
var NameComponent = (function () {
    function NameComponent(nameStore, names, elementRef) {
        this.nameStore = nameStore;
        this.names = names;
        this.elementRef = elementRef;
        this.toggle = false;
        this.saveLock = false;
        this.generating = false;
        this.userAuth = false;
    }
    NameComponent.prototype.onInit = function () {
        var _this = this;
        // init saved names
        var nativeElement = this.elementRef.nativeElement;
        var saved = nativeElement.getAttribute('saved-names') || '[]';
        var generated = nativeElement.getAttribute('generated-names') || '[]';
        var namesJson = JSON.parse(saved);
        var generatedJson = JSON.parse(generated);
        namesJson.forEach(function (item) { return _this.saved.push(new name_1.Name(item.text, item.key)); });
        generatedJson.forEach(function (item) { return _this.generated.push(new name_1.Name(item.text, -1)); });
        // init user auth
        this.userAuth = nativeElement.getAttribute('user-auth') || false;
        this.trainingName = JSON.parse(nativeElement.getAttribute('training-name'));
        this.nameAttributes = nativeElement.getAttribute('name-attributes').match(/(\w+)/g);
    };
    Object.defineProperty(NameComponent.prototype, "generated", {
        get: function () {
            return this.names.getGenerated();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(NameComponent.prototype, "saved", {
        get: function () {
            return this.names.getSaved();
        },
        enumerable: true,
        configurable: true
    });
    NameComponent.prototype.generate = function (num) {
        var _this = this;
        if (num < 1) {
            return;
        }
        this.generating = true;
        this.nameStore.generate(num || 10).subscribe(function (res) {
            _this.generating = false;
        });
    };
    NameComponent.prototype.removeName = function (name) {
        return this.nameStore.remove(name);
    };
    NameComponent.prototype.saveName = function (name) {
        return this.nameStore.save(name);
    };
    NameComponent = __decorate([
        angular2_1.Component({
            selector: '[name-app]',
            templateUrl: 'templates/name.component.html',
            directives: [name_learn_1.NameLearn, name_list_1.NameList, angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [name_store_1.NameStore, name_1.Names, angular2_1.ElementRef])
    ], NameComponent);
    return NameComponent;
})();
exports.NameComponent = NameComponent;
angular2_1.bootstrap(NameComponent, [name_1.Names, name_store_1.NameStore]);
//# sourceMappingURL=name-app.js.map