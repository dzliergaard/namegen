import {Name} from '../name/name';

export class RacePop {
    constructor(public race:string, public population:number) {
    }
}

export class Population {
    constructor(public people:Array<RacePop>, public tot:number, public searchMod:number) {
    }
}

export class Ruler {
    constructor(public name:Name, public race:string) {
    }
}

export class City {
    constructor(public name:string, public ruler:Ruler, public population:Population, public inns:Array<string>) {
    }
}