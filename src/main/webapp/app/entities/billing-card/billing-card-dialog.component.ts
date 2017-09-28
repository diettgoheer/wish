import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BillingCard } from './billing-card.model';
import { BillingCardPopupService } from './billing-card-popup.service';
import { BillingCardService } from './billing-card.service';

@Component({
    selector: 'jhi-billing-card-dialog',
    templateUrl: './billing-card-dialog.component.html'
})
export class BillingCardDialogComponent implements OnInit {

    billingCard: BillingCard;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private billingCardService: BillingCardService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.billingCard.id !== undefined) {
            this.subscribeToSaveResponse(
                this.billingCardService.update(this.billingCard), false);
        } else {
            this.subscribeToSaveResponse(
                this.billingCardService.create(this.billingCard), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BillingCard>, isCreated: boolean) {
        result.subscribe((res: BillingCard) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BillingCard, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.billingCard.created'
            : 'wishApp.billingCard.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'billingCardListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-billing-card-popup',
    template: ''
})
export class BillingCardPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private billingCardPopupService: BillingCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.billingCardPopupService
                    .open(BillingCardDialogComponent, params['id']);
            } else {
                this.modalRef = this.billingCardPopupService
                    .open(BillingCardDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
