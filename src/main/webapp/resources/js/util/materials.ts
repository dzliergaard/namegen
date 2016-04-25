import {Injectable} from "angular2/core";
import _ = require("underscore");

/**
 * Provides convenience material-design class bundles to components
 */
@Injectable()
export class Materials {
    public cardClass(opts?:any, other?:string) {
        let output = ["mdl-card" + (opts["section"] ? "__" + opts["section"] : "")];

        if (opts && opts["main"]) {
            output.push("mdl-color--" + opts["main"]);
        }
        if (opts && opts["text"]) {
            output.push("mdl-color-text--" + opts["text"]);
        }

        return this.andOther(output, other);
    }

    public btnClass(opts:Array<string>, other?:string) {
        let output = ["mdl-button", "mdl-js-button", "mdl-js-ripple-effect"];
        if (this.hasOpt(opts, "primary")) {
            output.push("mdl-button--primary");
        } else {
            output.push("mdl-button--accent");
        }
        if (!this.hasOpt(opts, "flat")) {
            output.push("mdl-button--raised");
        }

        return this.andOther(output, other);
    }

    public cellClass(cols:{[key: string]: number}, offsets?:{[key: string]: number}, other?:string) {
        let output = ["mdl-cell"];
        output.push.apply(output, this.mapCellClasses(cols));

        if (offsets) {
            output.push.apply(output, this.mapCellClasses(offsets, true));
        }

        return this.andOther(output, other);
    }

    public fullCell(other?:string) {
        return this.cellClass({d: 12, t: 8, p: 6}, {}, other);
    }

    private mapCellClasses(list:any, offset?:boolean) {
        let tag = offset === true ? "-offset" : "-col";
        return _.map(list, (num:number, type) => {
            let retVal = "mdl-cell--" + num + tag;
            switch(type) {
                case "d":
                case "dsk":
                case "desktop":
                    return retVal + "-desktop";
                case "t":
                case "tab":
                case "tablet":
                    return retVal + "-tablet";
                case "p":
                case "pho":
                case "phone":
                    return retVal + "-phone";
                default:
                    return retVal;
            }
        });
    }

    private andOther(classes:Array<string>, other?:string) {
        if (other) {
            classes.push(other);
        }
        return classes.join(" ");
    }

    private hasOpt(opts:Array<string>, optName:string) {
        return opts && !!_.some(opts, opt => opt == optName);
    }
}