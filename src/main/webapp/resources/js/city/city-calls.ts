import {Inject, Injectable} from "angular2/core";
import {HttpHelper} from "util/http-helper";
import "rxjs/Rx";

/**
 * Http calls to endpoints surfaced by NameController (/city/*)
 */
@Injectable()
export class CityCalls {
    constructor(@Inject(HttpHelper) private http:HttpHelper) {
    }

    generate(num?:Number) {
        return this.http.get('/city/generate', {num: num || 10});
    }

    remove(name:any) {
        return this.http.post('/city/delete', name);
    }

    save(name:any) {
        return this.http.post('/city/save', name);
    }
}