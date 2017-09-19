import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Circle } from './circle.model';
import { CirclePopupService } from './circle-popup.service';
import { CircleService } from './circle.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-circle-dialog',
    templateUrl: './circle-dialog.component.html'
})
export class CircleDialogComponent implements OnInit {

    circle: Circle;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private circleService: CircleService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.circle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.circleService.update(this.circle), false);
        } else {
            this.subscribeToSaveResponse(
                this.circleService.create(this.circle), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Circle>, isCreated: boolean) {
        result.subscribe((res: Circle) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Circle, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.circle.created'
            : 'wishApp.circle.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'circleListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-circle-popup',
    template: ''
})
export class CirclePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private circlePopupService: CirclePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.circlePopupService
                    .open(CircleDialogComponent, params['id']);
            } else {
                this.modalRef = this.circlePopupService
                    .open(CircleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
