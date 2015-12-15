var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
var entity_store_1 = require('../util/entity-store');
var name_1 = require('./name');
var NameStore = (function (_super) {
    __extends(NameStore, _super);
    function NameStore(names) {
        _super.call(this, "name");
        this.names = names;
    }
    Object.defineProperty(NameStore.prototype, "saved", {
        get: function () {
            return this.names.getSaved();
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(NameStore.prototype, "generated", {
        get: function () {
            return this.names.getGenerated();
        },
        enumerable: true,
        configurable: true
    });
    NameStore.prototype.generate = function (num) {
        var _this = this;
        var answer = _super.prototype.generate.call(this, { num: num });
        answer.subscribe(function (res) {
            var nameList = res.json().map(function (item) { return new name_1.Name(item.text, item.key); });
            _this.generated.splice(0, _this.generated.length);
            _this.generated.push.apply(_this.generated, nameList);
        });
        return answer;
    };
    NameStore.prototype.updateName = function (name, res) {
        name.key = res.json().key;
        console.log("Name updated: " + res.json());
    };
    NameStore.prototype.saveName = function (res) {
        var name = new name_1.Name(res.json().text, res.json().key);
        this.saved.push(name);
        console.log("New name saved: " + JSON.stringify(res.json()));
    };
    NameStore.prototype.save = function (name) {
        var _this = this;
        _super.prototype.save.call(this, name).subscribe(function (res) {
            name.key < 0 ? _this.saveName(res) : _this.updateName(name, res);
        });
    };
    NameStore.prototype.remove = function (name) {
        var _this = this;
        _super.prototype.remove.call(this, name).subscribe(function (res) { return _this.names.remove(name); });
    };
    NameStore.prototype.train = function (name) {
        return this.caller.post('/name/train', name);
    };
    NameStore = __decorate([
        angular2_1.Injectable(), 
        __metadata('design:paramtypes', [name_1.Names])
    ], NameStore);
    return NameStore;
})(entity_store_1.EntityStore);
exports.NameStore = NameStore;
//# sourceMappingURL=name-store.js.map