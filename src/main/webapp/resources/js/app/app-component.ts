import {Component, Inject, AfterViewInit, ElementRef} from "angular2/core";
import {ROUTER_DIRECTIVES, ROUTER_PROVIDERS, RouteConfig} from "angular2/router";
import {CORE_DIRECTIVES, FORM_DIRECTIVES} from "angular2/common";
import {HTTP_PROVIDERS} from "angular2/http";
import {UserData} from "app/user-data";
import {CityComponent} from "city/city-component";
import {NameComponent} from "name/name-component";
import {HttpHelper} from "util/http-helper";
declare var componentHandler;

/**
 * Parent level application with routing config to switch between name and city
 * views, and behavior to sign in/out using Google's OAuth2 authentication.
 */
@Component({
    selector: 'rptools-app',
    templateUrl: 'templates/app.component.html',
    directives: [NameComponent, CORE_DIRECTIVES, FORM_DIRECTIVES, ROUTER_DIRECTIVES],
    providers: [
        HttpHelper,
        UserData,
        HTTP_PROVIDERS,
        ROUTER_PROVIDERS
    ]
})
@RouteConfig([
    {
        path: '/name',
        name: 'Name',
        component: NameComponent,
        useAsDefault: true
    },
    {
        path: '/city',
        name: 'City',
        component: CityComponent
    }
])
export class AppComponent implements AfterViewInit {
    constructor(@Inject(UserData) private userData:UserData, @Inject(ElementRef) private elementRef:ElementRef){
    }

    ngAfterViewInit() {
        componentHandler.upgradeElements(this.elementRef.nativeElement.children);
    }

    signIn() {
        var state = {
            returnUri: [window.location.pathname]
        };
        window.location.href = "/googleAuth?state=" + encodeURI(JSON.stringify(state));
    }

    signOut() {
        window.location.href = "/googleAuth/signOut?returnUri=" + window.location.pathname;
    }
}