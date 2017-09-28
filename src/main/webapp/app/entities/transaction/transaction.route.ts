import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TransactionComponent } from './transaction.component';
import { TransactionDetailComponent } from './transaction-detail.component';
import { TransactionPopupComponent } from './transaction-dialog.component';
import { TransactionDeletePopupComponent } from './transaction-delete-dialog.component';

import { Principal } from '../../shared';

export const transactionRoute: Routes = [
    {
        path: 'transaction',
        component: TransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transaction/:id',
        component: TransactionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionPopupRoute: Routes = [
    {
        path: 'transaction-new',
        component: TransactionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction/:id/edit',
        component: TransactionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction/:id/delete',
        component: TransactionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
