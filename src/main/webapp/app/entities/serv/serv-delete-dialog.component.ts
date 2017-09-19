import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Serv } from './serv.model';
import { ServPopupService } from './serv-popup.service';
import { ServService } from './serv.service';

@Component({
    selector: 'jhi-serv-delete-dialog',
    templateUrl: './serv-delete-dialog.component.html'
})
export class ServDeleteDialogComponent {

    serv: Serv;

    constructor(
        private servService: ServService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'servListModification',
                content: 'Deleted an serv'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('wishApp.serv.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-serv-delete-popup',
    template: ''
})
export class ServDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servPopupService: ServPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.servPopupService
                .open(ServDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
