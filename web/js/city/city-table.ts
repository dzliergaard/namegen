import {Component, EventEmitter, Input, Output, CORE_DIRECTIVES} from 'angular2/angular2';
import {City} from './city';

@Component({
    selector: 'tr[city-row]',
    directives: [CORE_DIRECTIVES],
    template: `
        <th *ng-if="isHeader" class="col-xs-2">{{heading}}:</th>
        <th *ng-if="isHeader"><strong *ng-for="#value of values">{{value}}<br/></strong></th>
        <th *ng-if="isHeader"><a type='button' class='btn btn-default btn-sm' (click)="refresh.next()">
            <span class='glyphicon glyphicon-refresh'></span>
        </a></th>
        <td *ng-if="!isHeader" class="col-xs-2">{{heading}}:</td>
        <td *ng-if="!isHeader"><strong *ng-for="#value of values">{{value}}<br/></strong></td>
        <td *ng-if="!isHeader"><a type='button' class='btn btn-default btn-sm' (click)="refresh.next()">
            <span class='glyphicon glyphicon-refresh'></span>
        </a></td>
    `
})
class TableRow {
    @Input() isHeader:boolean;
    @Input() heading:string;
    @Input() values:string;
    @Output() refresh = new EventEmitter();
}

@Component({
    selector: 'city',
    directives: [TableRow, CORE_DIRECTIVES],
    template: `
        <table class='table table-bordered table-striped row'>
            <thead>
                <tr city-row heading="Name" [is-header]="true" [values]="[city.name]" (refresh)="refreshAttr.next('name')"></tr>
            </thead>
            <tbody>
                <tr city-row heading="Ruler" [values]="formatRuler()" (refresh)="refreshAttr.next('ruler')"></tr>
                <tr city-row heading="Population" [values]="formatPopulation()" (refresh)="refreshAttr.next('population')"></tr>
                <tr city-row heading="Inns/Shoppes" [values]="city.inns" (refresh)="refreshAttr.next('inns')"></tr>
            </tbody>
        </table>
    `
})
export class CityTable {
    @Input() city:City;
    @Output() refreshAttr = new EventEmitter();

    formatRuler() {
        return [this.city.ruler.name.text + ' (' + this.city.ruler.race + ')'];
    }

    formatPopulation() {
        return this.city.population.people.map(racePop => racePop.race + ' (' + racePop.population + ')');
    }
}