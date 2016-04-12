import {Directive, Inject, Input, AfterViewInit} from "angular2/core";
import {UserContent} from "app/user-content";
import {Name} from "name/name";
import {NameStore} from "name/name-store";
import {InputStrongData} from "util/input-strong";

export enum NameStoreAction {
    Save,
    Remove,
    Train
}

/**
 * Any {@link Name} or {@link TrainingName} directive needs to have one of these on a parent element. The type
 * of name-action determines 
 */
@Directive({
    selector: '[name-action]'
})
export class NameStoreDirective implements AfterViewInit {
    @Input('name-action') private nameAction:NameStoreAction;

    public primary:Function = function(){};
    public secondary:Function = function(){};

    constructor(@Inject(NameStore) private nameStore:NameStore, @Inject(UserContent) private userContent:UserContent) {
    }

    ngAfterViewInit() {
        this.primary = {
            "save": this.nameStore.save,
            "NameStoreAction.Remove": this.nameStore.remove,
            "train": this.nameStore.train
        }[this.nameAction] || this.primary;

        if (this.nameAction == NameStoreAction.Remove) {
            this.secondary = this.nameStore.save;
        }
    }

    btnText(name:InputStrongData) {
        return {
            "save": name.editing ? "Done" : "Save",
            "remove": name.editing ? "Done" : "Remove"
        }[this.nameAction];
    }

    btnDisabled(name:InputStrongData) {
        return this.nameAction == "save" && name.editing;
    }

    private static doNothing(){
    }
}

// @Directive({
//     selector: '[name-save]'
// })
// export class SaveNameDirective implements NameStoreDirective {
//     constructor(@Inject(NameStore) private nameStore:NameStore, @Inject(UserContent) private userContent:UserContent) {
//     }
//
//     doPrimary(name:any) {
//         this.nameStore.save(name);
//     }
//
//     doSecondary(name:any){
//     }
//
//     btnText() {
//         return "Save";
//     }
//
//     btnDisabled(name:any) {
//         return !name.editing && !this.userContent.isSignedIn;
//     }
// }

@Directive({
    selector: '[name-remove]'
})
export class RemoveNameDirective implements NameStoreDirective {
    constructor(@Inject(NameStore) private nameStore:NameStore) {
    }

    doPrimary(name:any) {
        this.nameStore.remove(name);
    }

    doSecondary(name:any) {
        this.nameStore.save(name);
    }

    btnText() {
        return "Remove";
    }

    btnDisabled(name:any) {
        return false;
    }
}

@Directive({
    selector: '[name-train]'
})
export class TrainNameDirective implements NameStoreDirective {
    constructor(@Inject(NameStore) private nameStore:NameStore) {
    }

    doPrimary(name:any) {
        this.nameStore.train(name);
    }

    doSecondary(name:any) {
    }

    btnText() {
    }

    btnDisabled(name:any) {
        return false;
    }
}


// @Directive({
//     selector: '[name-action]',
//     inputs: ['nameDirType: name-action']
// })
// export class NameStoreDirective implements OnInit {
//     private dir:NameStoreAction;
//     private nameDirType:string;
//
//     constructor(@Inject(NameStore) private nameStore:NameStore, @Inject('UserAuth') private userAuthed:boolean){
//     }
//
//     ngOnInit() {
//         switch (this.nameDirType) {
//             case 'save':
//                 this.dir = new SaveNameAction(this.nameStore, this.userAuthed);
//                 break;
//             case 'remove':
//                 this.dir = new RemoveNameAction(this.nameStore);
//                 break;
//             case 'train':
//                 this.dir = new TrainNameAction(this.nameStore);
//                 break;
//         }
//     }
//
//     doPrimary(name:any){
//         return this.dir.doPrimary(name);
//     }
//
//     doSecondary(name:any){
//         return this.dir.doSecondary(name);
//     }
//
//     btnText(){
//         return this.dir.btnText();
//     }
//
//     btnDisabled(name:any){
//         return this.dir.btnDisabled(name);
//     }
// }

// class NameStoreAction {
//
//     doPrimary(name:any){}
//
//     doSecondary(name:any){}
//
//     btnText(){}
//
//     btnDisabled(name:any){
//         return false;
//     }
// }
//
// class SaveNameAction extends NameStoreAction {
//     constructor(private nameStore:NameStore, private userAuthed:boolean) {
//         super();
//     }
//
//     doPrimary(name:any) {
//         this.nameStore.save(name);
//     }
//
//     btnText() {
//         return "Save";
//     }
//
//     btnDisabled(name:any) {
//         return !name.editing && !this.userAuthed;
//     }
// }
//
// class RemoveNameAction extends NameStoreAction {
//     constructor(@Inject(NameStore) private nameStore:NameStore) {
//         super();
//     }
//
//     doPrimary(name:any) {
//         this.nameStore.remove(name);
//     }
//
//     doSecondary(name:any) {
//         this.nameStore.save(name);
//     }
//
//     btnText() {
//         return "Remove";
//     }
// }
//
// class TrainNameAction extends NameStoreAction {
//     constructor(@Inject(NameStore) private nameStore:NameStore) {
//         super();
//     }
//
//     doPrimary(name:any) {
//         this.nameStore.train(name);
//     }
// }
