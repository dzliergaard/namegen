import {ng, Injectable} from 'angular2/angular2';
import {RequestOptions} from 'angular2/http';
import {EntityStore} from '../util/entity-store';
import {Name, Names} from './name';

@Injectable()
export class NameStore extends EntityStore {
    constructor(private names:Names) {
        super("name");
    }

    get saved() {
        return this.names.getSaved();
    }

    get generated() {
        return this.names.getGenerated();
    }

    generate(num:number) {
        var answer = super.generate({num: num});
        answer.subscribe(res => {
            var nameList = res.json().map(item => new Name(item.text, item.key));
            this.generated.splice(0, this.generated.length);
            this.generated.push.apply(this.generated, nameList);
        });
        return answer;
    }

    private updateName(name:Name, res) {
        name.key = res.json().key;
        console.log("Name updated: " + res.json());
    }

    private saveName(res) {
        var name = new Name(res.json().text, res.json().key);
        this.saved.push(name);
        console.log("New name saved: " + JSON.stringify(res.json()));
    }

    save(name:Name) {
        super.save(name).subscribe(res => {
            name.key < 0 ? this.saveName(res) : this.updateName(name, res);
        });
    }

    remove(name:Name) {
        super.remove(name).subscribe(res => this.names.remove(name));
    }

    train(name:Name) {
        return this.caller.post('/name/train', name);
    }
}