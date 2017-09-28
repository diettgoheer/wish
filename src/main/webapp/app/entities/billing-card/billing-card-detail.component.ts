import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BillingCard } from './billing-card.model';
import { BillingCardService } from './billing-card.service';

@Component({
    selector: 'jhi-billing-card-detail',
    templateUrl: './billing-card-detail.component.html'
})
export class BillingCardDetailComponent implements OnInit, OnDestroy {

    billingCard: BillingCard;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private billingCardService: BillingCardService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBillingCards();
    }

    load(id) {
        this.billingCardService.find(id).subscribe((billingCard) => {
            this.billingCard = billingCard;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBillingCards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'billingCardListModification',
            (response) => this.load(this.billingCard.id)
        );
    }
}
