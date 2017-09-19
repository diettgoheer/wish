import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Serv } from './serv.model';
import { ServPopupService } from './serv-popup.service';
import { ServService } from './serv.service';
import { Person, PersonService } from '../person';
import { ServiceProvider, ServiceProviderService } from '../service-provider';
import { Work, WorkService } from '../work';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-serv-dialog',
    templateUrl: './serv-dialog.component.html'
})
export class ServDialogComponent implements OnInit {

    serv: Serv;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    servs: Serv[];

    serviceproviders: ServiceProvider[];

    works: Work[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private servService: ServService,
        private personService: PersonService,
        private serviceProviderService: ServiceProviderService,
        private workService: WorkService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.servService.query()
            .subscribe((res: ResponseWrapper) => { this.servs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.serviceProviderService.query()
            .subscribe((res: ResponseWrapper) => { this.serviceproviders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.workService.query()
            .subscribe((res: ResponseWrapper) => { this.works = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, serv, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                serv[field] = base64Data;
                serv[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.serv.id !== undefined) {
            this.subscribeToSaveResponse(
                this.servService.update(this.serv), false);
        } else {
            this.subscribeToSaveResponse(
                this.servService.create(this.serv), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Serv>, isCreated: boolean) {
        result.subscribe((res: Serv) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Serv, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.serv.created'
            : 'wishApp.serv.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'servListModification', content: 'OK'});
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

    trackServById(index: number, item: Serv) {
        return item.id;
    }

    trackServiceProviderById(index: number, item: ServiceProvider) {
        return item.id;
    }

    trackWorkById(index: number, item: Work) {
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
    selector: 'jhi-serv-popup',
    template: ''
})
export class ServPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servPopupService: ServPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.servPopupService
                    .open(ServDialogComponent, params['id']);
            } else {
                this.modalRef = this.servPopupService
                    .open(ServDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
