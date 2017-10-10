import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Transaction } from './transaction.model';
import { TransactionService } from './transaction.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {BillingCard} from '../billing-card/billing-card.model';
import {BillingCardService} from '../billing-card/billing-card.service';

@Component({
    selector: 'jhi-transaction',
    templateUrl: './transaction.component.html'
})
export class TransactionComponent implements OnInit, OnDestroy {
    transactions: Transaction[];
    billingCard: BillingCard;
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private transactionService: TransactionService,
        private billingCardService: BillingCardService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        this.billingCardService.findMine().subscribe((billingCard) => {
            this.billingCard = billingCard;
        });
        if (this.currentSearch) {
            this.transactionService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.transactions = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.transactionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.transactions = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTransactions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    previousState() {
        window.history.back();
    }

    trackId(index: number, item: Transaction) {
        return item.id;
    }
    registerChangeInTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('transactionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
