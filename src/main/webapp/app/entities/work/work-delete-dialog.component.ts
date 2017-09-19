import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Work } from './work.model';
import { WorkPopupService } from './work-popup.service';
import { WorkService } from './work.service';

@Component({
    selector: 'jhi-work-delete-dialog',
    templateUrl: './work-delete-dialog.component.html'
})
export class WorkDeleteDialogComponent {

    work: Work;

    constructor(
        private workService: WorkService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workListModification',
                content: 'Deleted an work'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('wishApp.work.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-work-delete-popup',
    template: ''
})
export class WorkDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workPopupService: WorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.workPopupService
                .open(WorkDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
