import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WishSharedModule } from '../../shared';
import {
    CircleService,
    CirclePopupService,
    CircleComponent,
    CircleDetailComponent,
    CircleDialogComponent,
    CirclePopupComponent,
    CircleDeletePopupComponent,
    CircleDeleteDialogComponent,
    circleRoute,
    circlePopupRoute,
} from './';

const ENTITY_STATES = [
    ...circleRoute,
    ...circlePopupRoute,
];

@NgModule({
    imports: [
        WishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CircleComponent,
        CircleDetailComponent,
        CircleDialogComponent,
        CircleDeleteDialogComponent,
        CirclePopupComponent,
        CircleDeletePopupComponent,
    ],
    entryComponents: [
        CircleComponent,
        CircleDialogComponent,
        CirclePopupComponent,
        CircleDeleteDialogComponent,
        CircleDeletePopupComponent,
    ],
    providers: [
        CircleService,
        CirclePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WishCircleModule {}
