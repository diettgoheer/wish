import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BillingCard } from './billing-card.model';
import { BillingCardPopupService } from './billing-card-popup.service';
import { BillingCardService } from './billing-card.service';

@Component({
    selector: 'jhi-billing-card-delete-dialog',
    templateUrl: './billing-card-delete-dialog.component.html'
})
export class BillingCardDeleteDialogComponent {

    billingCard: BillingCard;

    constructor(
        private billingCardService: BillingCardService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billingCardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'billingCardListModification',
                content: 'Deleted an billingCard'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('wishApp.billingCard.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-billing-card-delete-popup',
    template: ''
})
export class BillingCardDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private billingCardPopupService: BillingCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.billingCardPopupService
                .open(BillingCardDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
