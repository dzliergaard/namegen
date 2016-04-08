import {Injectable, Inject} from "angular2/core";
import {Http, RequestOptions, Response, Headers} from "angular2/http";
import {Observable} from "rxjs/Observable";
import _ = require("underscore");

/**
 * Wrapper around Http that adds .map and .catch calls to Observable response
 */
@Injectable()
export class HttpHelper {
    private static headers:Headers = new Headers({"Content-Type": "application/json"});
    private static requestOptions:RequestOptions = new RequestOptions({headers: HttpHelper.headers});

    constructor(@Inject(Http) private http:Http){
    }

    get(url:string, search?:any) {
        let options = HttpHelper.requestOptions;
        if (search) {
            options = new RequestOptions({
                search: HttpHelper.toQueryParamString(search),
                headers: HttpHelper.headers
            });
        }
        return this.http.get(url, options)
            .map(res => HttpHelper.parseResponse(res))
            .catch(HttpHelper.handleError);
    }

    post(url:string, body?:any) {
        return this.http.post(url, JSON.stringify(body || {}), HttpHelper.requestOptions)
            .map(res => HttpHelper.parseResponse(res))
            .catch(HttpHelper.handleError);
    }

    private static parseResponse(response:Response) {
        if (response.text() == "" || _.isUndefined(response.json())) {
            return response;
        }
        return _.isUndefined(response.json().data) ? response.json() : response.json().data;
    }

    private static handleError(error:Response) {
        console.error(error.message);
        return Observable.throw('Error in REST call: ' + JSON.stringify(error.message));
    }

    private static toQueryParamString(search:any) {
        return _.reduce(search, (m, v, k) => {
            return (m ? m + "&" : "") + k + "=" + v;
        }, null);
    }
}