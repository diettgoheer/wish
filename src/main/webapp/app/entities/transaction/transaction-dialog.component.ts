import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Transaction } from './transaction.model';
import { TransactionPopupService } from './transaction-popup.service';
import { TransactionService } from './transaction.service';
import { Work, WorkService } from '../work';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-transaction-dialog',
    templateUrl: './transaction-dialog.component.html'
})
export class TransactionDialogComponent implements OnInit {

    transaction: Transaction;
    authorities: any[];
    isSaving: boolean;

    works: Work[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private transactionService: TransactionService,
        private workService: WorkService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.workService.query()
            .subscribe((res: ResponseWrapper) => { this.works = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transaction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transactionService.update(this.transaction), false);
        } else {
            this.subscribeToSaveResponse(
                this.transactionService.create(this.transaction), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Transaction>, isCreated: boolean) {
        result.subscribe((res: Transaction) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Transaction, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.transaction.created'
            : 'wishApp.transaction.updated',
            { param : result.name }, null);

        this.eventManager.broadcast({ name: 'transactionListModification', content: 'OK'});
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

    trackWorkById(index: number, item: Work) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transaction-popup',
    template: ''
})
export class TransactionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.transactionPopupService
                    .open(TransactionDialogComponent, params['id']);
            } else {
                this.modalRef = this.transactionPopupService
                    .open(TransactionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
