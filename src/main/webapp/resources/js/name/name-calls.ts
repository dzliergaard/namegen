import {Inject, Injectable} from "angular2/core";
import {HttpHelper} from "util/http-helper";
import "rxjs/Rx";

/**
 * Http calls to endpoints surfaced by NameController (/name/*)
 */
@Injectable()
export class NameCalls {
    constructor(@Inject(HttpHelper) private http:HttpHelper) {
    }

    generate(num?:Number) {
        return this.http.get('/name/generate', {num: num || 10});
    }

    remove(name:any) {
        return this.http.post('/name/delete', name);
    }

    save(name:any) {
        return this.http.post('/name/save', name);
    }

    train(name:any) {
        return this.http.post('/name/train', name);
    }

    attributes() {
        return this.http.get('/name/nameAttributes');
    }
}