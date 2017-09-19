import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WishSharedModule } from '../../shared';
import {
    ServiceProviderService,
    ServiceProviderPopupService,
    ServiceProviderComponent,
    ServiceProviderDetailComponent,
    ServiceProviderDialogComponent,
    ServiceProviderPopupComponent,
    ServiceProviderDeletePopupComponent,
    ServiceProviderDeleteDialogComponent,
    serviceProviderRoute,
    serviceProviderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...serviceProviderRoute,
    ...serviceProviderPopupRoute,
];

@NgModule({
    imports: [
        WishSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServiceProviderComponent,
        ServiceProviderDetailComponent,
        ServiceProviderDialogComponent,
        ServiceProviderDeleteDialogComponent,
        ServiceProviderPopupComponent,
        ServiceProviderDeletePopupComponent,
    ],
    entryComponents: [
        ServiceProviderComponent,
        ServiceProviderDialogComponent,
        ServiceProviderPopupComponent,
        ServiceProviderDeleteDialogComponent,
        ServiceProviderDeletePopupComponent,
    ],
    providers: [
        ServiceProviderService,
        ServiceProviderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WishServiceProviderModule {}
