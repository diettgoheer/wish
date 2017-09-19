import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ServiceProvider } from './service-provider.model';
import { ServiceProviderPopupService } from './service-provider-popup.service';
import { ServiceProviderService } from './service-provider.service';

@Component({
    selector: 'jhi-service-provider-dialog',
    templateUrl: './service-provider-dialog.component.html'
})
export class ServiceProviderDialogComponent implements OnInit {

    serviceProvider: ServiceProvider;
    authorities: any[];
    isSaving: boolean;
    enterpriseCreatedTimeDp: any;
    certificationTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private serviceProviderService: ServiceProviderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, serviceProvider, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                serviceProvider[field] = base64Data;
                serviceProvider[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.serviceProvider.id !== undefined) {
            this.subscribeToSaveResponse(
                this.serviceProviderService.update(this.serviceProvider), false);
        } else {
            this.subscribeToSaveResponse(
                this.serviceProviderService.create(this.serviceProvider), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ServiceProvider>, isCreated: boolean) {
        result.subscribe((res: ServiceProvider) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ServiceProvider, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.serviceProvider.created'
            : 'wishApp.serviceProvider.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'serviceProviderListModification', content: 'OK'});
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
    selector: 'jhi-service-provider-popup',
    template: ''
})
export class ServiceProviderPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceProviderPopupService: ServiceProviderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.serviceProviderPopupService
                    .open(ServiceProviderDialogComponent, params['id']);
            } else {
                this.modalRef = this.serviceProviderPopupService
                    .open(ServiceProviderDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
