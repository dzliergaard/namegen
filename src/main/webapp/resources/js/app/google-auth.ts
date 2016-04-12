import {Inject, Injectable} from "angular2/core";
import {Http} from "angular2/http";

@Injectable()
export class GoogleAuth {
    constructor(@Inject(Http) private http:Http) {
    }
}
