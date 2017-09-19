import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Work } from './work.model';
import { WorkPopupService } from './work-popup.service';
import { WorkService } from './work.service';
import { Person, PersonService } from '../person';
import { Project, ProjectService } from '../project';
import { Serv, ServService } from '../serv';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-work-dialog',
    templateUrl: './work-dialog.component.html'
})
export class WorkDialogComponent implements OnInit {

    work: Work;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    projects: Project[];

    servs: Serv[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private workService: WorkService,
        private personService: PersonService,
        private projectService: ProjectService,
        private servService: ServService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.projectService.query()
            .subscribe((res: ResponseWrapper) => { this.projects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.servService.query()
            .subscribe((res: ResponseWrapper) => { this.servs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.work.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workService.update(this.work), false);
        } else {
            this.subscribeToSaveResponse(
                this.workService.create(this.work), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Work>, isCreated: boolean) {
        result.subscribe((res: Work) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Work, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.work.created'
            : 'wishApp.work.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'workListModification', content: 'OK'});
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

    trackProjectById(index: number, item: Project) {
        return item.id;
    }

    trackServById(index: number, item: Serv) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-work-popup',
    template: ''
})
export class WorkPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workPopupService: WorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.workPopupService
                    .open(WorkDialogComponent, params['id']);
            } else {
                this.modalRef = this.workPopupService
                    .open(WorkDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
