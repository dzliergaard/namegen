import {Injectable, Inject} from "angular2/core";
import {HttpHelper} from "util/http-helper";

/**
 * DOM Injectable singleton equivalent to com.rptools.data.UserSavedContent
 * Contains a user's saved name and city rp entities. main.jsp sets this
 * as a meta element server-side, so this just needs to read and parse it
 * upon creation.
 */
@Injectable()
export class UserData {
    public isSignedIn:boolean = false;
    public generating:boolean = false;
    public generatedNames:any[] = [];
    public savedNames:any[] = [];
    public generatedCity:any = {};
    public savedCities:any[] = [];

    constructor(@Inject(HttpHelper) http:HttpHelper) {
        http.get("/googleAuth/isSignedIn").subscribe(res => this.getUserSavedContent(http, res));
    }

    private getUserSavedContent(http:HttpHelper, result:any) {
        this.isSignedIn = result;
        if (this.isSignedIn) {
            http.get("/userSavedContent").subscribe(res => this.parseUserContent(res));
        }
    }
    
    private parseUserContent(content:any) {
        if(content['names']) {
            this.savedNames = content['names'];
        }
        if (content['cities']) {
            this.savedCities = content['cities'];
        }
    }
}
