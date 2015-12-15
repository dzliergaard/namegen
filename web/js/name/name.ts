export class Name {
    public attribute:string;
    constructor (public text: string, public key: number){ }
}

export class Names {
    generated:Array<Name>;
    saved:Array<Name>;

    constructor(){
        this.generated = [];
        this.saved = [];
    }

    getGenerated () {
        return this.generated;
    }

    getSaved () {
        return this.saved;
    }

    setSaved () {
        return
    }

    remove (item) {
        this.saved = _.reject(this.saved, item);
    }
}