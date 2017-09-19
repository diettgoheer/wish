import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ServComponent } from './serv.component';
import { ServDetailComponent } from './serv-detail.component';
import { ServPopupComponent } from './serv-dialog.component';
import { ServDeletePopupComponent } from './serv-delete-dialog.component';

import { Principal } from '../../shared';

export const servRoute: Routes = [
    {
        path: 'serv',
        component: ServComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.serv.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'serv/:id',
        component: ServDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.serv.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servPopupRoute: Routes = [
    {
        path: 'serv-new',
        component: ServPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.serv.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'serv/:id/edit',
        component: ServPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.serv.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'serv/:id/delete',
        component: ServDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wishApp.serv.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
