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

    generate(body) {
        return this.http.get('/city/generate', body);
    }

    remove(city:any) {
        return this.http.post('/city/delete', city);
    }

    save(city:any) {
        return this.http.post('/city/save', city);
    }

    variables() {
        return this.http.get('/city/variableChoices');
    }
}