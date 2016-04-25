import {Type} from "angular2/core";
import {CONST_EXPR} from "angular2/src/facade/lang";
import {MdlButton} from "./mdl-button";
import {MdlCard, MdlCardTitle, MdlCardActions} from "./mdl-card";
import {MdlGrid, MdlCell} from "./mdl-grid";
import {MdlList, MdlListItem} from "./mdl-list";
import {MdlSpinner} from "./mdl-spinner";

export const MDL_COMPONENTS: Type[] = CONST_EXPR([
    MdlButton,
    MdlCard,
    MdlCardTitle,
    MdlCardActions,
    MdlGrid,
    MdlCell,
    MdlList,
    MdlListItem,
    MdlSpinner
]);