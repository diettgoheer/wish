import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WishSharedModule } from '../../shared';
import {
    ServService,
    ServPopupService,
    ServComponent,
    ServDetailComponent,
    ServDialogComponent,
    ServPopupComponent,
    ServDeletePopupComponent,
    ServDeleteDialogComponent,
    servRoute,
    servPopupRoute,
} from './';

const ENTITY_STATES = [
    ...servRoute,
    ...servPopupRoute,
];

@NgModule({
    imports: [
        WishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServComponent,
        ServDetailComponent,
        ServDialogComponent,
        ServDeleteDialogComponent,
        ServPopupComponent,
        ServDeletePopupComponent,
    ],
    entryComponents: [
        ServComponent,
        ServDialogComponent,
        ServPopupComponent,
        ServDeleteDialogComponent,
        ServDeletePopupComponent,
    ],
    providers: [
        ServService,
        ServPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WishServModule {}
