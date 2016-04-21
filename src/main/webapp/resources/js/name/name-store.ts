import {Inject, Injectable} from "angular2/core";
import {NameCalls} from "name/name-calls";
import _ = require('underscore');

@Injectable()
export class NameStore {
    constructor(@Inject(NameCalls) private caller:NameCalls) {
    }

    generate(num:number) {
        return this.caller.generate(num);
    }

    save(name:any) {
        return this.caller.save(name);
    }

    remove(name:any) {
        return this.caller.remove(name);
    }

    train(name:any) {
        return this.caller.train(name);
    }

    attributes() {
        return this.caller.attributes();
    }
}