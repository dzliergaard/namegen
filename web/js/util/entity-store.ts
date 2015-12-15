import {bootstrap, Inject, Injectable, Injector} from 'angular2/angular2';
import {Headers, Http, HTTP_PROVIDERS, RequestOptions, RequestMethods} from 'angular2/http';
import _ = require('underscore');

@Injectable()
class EntityCalls {
    requestOptions = new RequestOptions({
        headers: new Headers({"Content-Type": "application/json"})
    });

    constructor(public http:Http) {
    }

    generate(type:string, request:any) {
        return this.http.request('/' + type + '/generate', new RequestOptions({
            search: _.reduce(request, function (memo, item, key) {
                return item ? memo + "&" + key + "=" + item : memo;
            }, "?")
        }));
    }

    save(type:string, item:any) {
        return this.http.post('/' + type + '/save', JSON.stringify(item), this.requestOptions);
    }

    remove(type:string, item:any) {
        return this.http.post('/' + type + '/delete', JSON.stringify(item), this.requestOptions);
    }

    // generic post method to support any unique operations
    post(url:string, body:any) {
        return this.http.post(url, JSON.stringify(body), this.requestOptions);
    }
}

export class EntityStore {
    public caller:EntityCalls;

    constructor(entityType:string) {
        this.type = entityType;
        var injector = Injector.resolveAndCreate([HTTP_PROVIDERS, EntityCalls]);
        this.caller = injector.get(EntityCalls);
    }

    generate(request:any) {
        return this.caller.generate(this.type, request);
    }

    save(item:any) {
        return this.caller.save(this.type, item);
    }

    remove(item:any) {
        return this.caller.remove(this.type, item);
    }
}