import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BillingCardComponent } from './billing-card.component';
import { BillingCardDetailComponent } from './billing-card-detail.component';
import { BillingCardPopupComponent } from './billing-card-dialog.component';
import { BillingCardDeletePopupComponent } from './billing-card-delete-dialog.component';

import { Principal } from '../../shared';

export const billingCardRoute: Routes = [
    {
        path: 'billing-card',
        component: BillingCardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.billingCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'billing-card/:id',
        component: BillingCardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.billingCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billingCardPopupRoute: Routes = [
    {
        path: 'billing-card-new',
        component: BillingCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.billingCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'billing-card/:id/edit',
        component: BillingCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.billingCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'billing-card/:id/delete',
        component: BillingCardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.billingCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
