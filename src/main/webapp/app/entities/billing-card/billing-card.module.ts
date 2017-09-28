import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WishSharedModule } from '../../shared';
import {
    BillingCardService,
    BillingCardPopupService,
    BillingCardComponent,
    BillingCardDetailComponent,
    BillingCardDialogComponent,
    BillingCardPopupComponent,
    BillingCardDeletePopupComponent,
    BillingCardDeleteDialogComponent,
    billingCardRoute,
    billingCardPopupRoute,
} from './';

const ENTITY_STATES = [
    ...billingCardRoute,
    ...billingCardPopupRoute,
];

@NgModule({
    imports: [
        WishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BillingCardComponent,
        BillingCardDetailComponent,
        BillingCardDialogComponent,
        BillingCardDeleteDialogComponent,
        BillingCardPopupComponent,
        BillingCardDeletePopupComponent,
    ],
    entryComponents: [
        BillingCardComponent,
        BillingCardDialogComponent,
        BillingCardPopupComponent,
        BillingCardDeleteDialogComponent,
        BillingCardDeletePopupComponent,
    ],
    providers: [
        BillingCardService,
        BillingCardPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WishBillingCardModule {}
