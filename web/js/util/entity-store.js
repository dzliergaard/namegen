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
var http_1 = require('angular2/http');
var _ = require('underscore');
var EntityCalls = (function () {
    function EntityCalls(http) {
        this.http = http;
        this.requestOptions = new http_1.RequestOptions({
            headers: new http_1.Headers({ "Content-Type": "application/json" })
        });
    }
    EntityCalls.prototype.generate = function (type, request) {
        return this.http.request('/' + type + '/generate', new http_1.RequestOptions({
            search: _.reduce(request, function (memo, item, key) {
                return item ? memo + "&" + key + "=" + item : memo;
            }, "?")
        }));
    };
    EntityCalls.prototype.save = function (type, item) {
        return this.http.post('/' + type + '/save', JSON.stringify(item), this.requestOptions);
    };
    EntityCalls.prototype.remove = function (type, item) {
        return this.http.post('/' + type + '/delete', JSON.stringify(item), this.requestOptions);
    };
    // generic post method to support any unique operations
    EntityCalls.prototype.post = function (url, body) {
        return this.http.post(url, JSON.stringify(body), this.requestOptions);
    };
    EntityCalls = __decorate([
        angular2_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], EntityCalls);
    return EntityCalls;
})();
var EntityStore = (function () {
    function EntityStore(entityType) {
        this.type = entityType;
        var injector = angular2_1.Injector.resolveAndCreate([http_1.HTTP_PROVIDERS, EntityCalls]);
        this.caller = injector.get(EntityCalls);
    }
    EntityStore.prototype.generate = function (request) {
        return this.caller.generate(this.type, request);
    };
    EntityStore.prototype.save = function (item) {
        return this.caller.save(this.type, item);
    };
    EntityStore.prototype.remove = function (item) {
        return this.caller.remove(this.type, item);
    };
    return EntityStore;
})();
exports.EntityStore = EntityStore;
//# sourceMappingURL=entity-store.js.map