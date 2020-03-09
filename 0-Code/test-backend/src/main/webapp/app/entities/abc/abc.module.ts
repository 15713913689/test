import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared';
import {
    AbcComponent,
    AbcDetailComponent,
    AbcUpdateComponent,
    AbcDeletePopupComponent,
    AbcDeleteDialogComponent,
    abcRoute,
    abcPopupRoute
} from './';

const ENTITY_STATES = [...abcRoute, ...abcPopupRoute];

@NgModule({
    imports: [JhipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AbcComponent, AbcDetailComponent, AbcUpdateComponent, AbcDeleteDialogComponent, AbcDeletePopupComponent],
    entryComponents: [AbcComponent, AbcUpdateComponent, AbcDeleteDialogComponent, AbcDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterAbcModule {}
