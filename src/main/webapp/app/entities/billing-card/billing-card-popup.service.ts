import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BillingCard } from './billing-card.model';
import { BillingCardService } from './billing-card.service';

@Injectable()
export class BillingCardPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private billingCardService: BillingCardService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.billingCardService.find(id).subscribe((billingCard) => {
                billingCard.createdDate = this.datePipe
                    .transform(billingCard.createdDate, 'yyyy-MM-ddThh:mm');
                billingCard.updatedDate = this.datePipe
                    .transform(billingCard.updatedDate, 'yyyy-MM-ddThh:mm');
                this.billingCardModalRef(component, billingCard);
            });
        } else {
            return this.billingCardModalRef(component, new BillingCard());
        }
    }

    billingCardModalRef(component: Component, billingCard: BillingCard): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.billingCard = billingCard;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
