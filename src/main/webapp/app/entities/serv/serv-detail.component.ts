import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {JhiEventManager, JhiDataUtils, JhiAlertService} from 'ng-jhipster';

import { Serv } from './serv.model';
import { ServService } from './serv.service';
import {Principal} from '../../shared/auth/principal.service';
import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-serv-detail',
    templateUrl: './serv-detail.component.html'
})
export class ServDetailComponent implements OnInit, OnDestroy {

    isManager: Boolean;
    isSaving: Boolean;
    serv: Serv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        /*public activeModal: NgbActiveModal,*/
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private servService: ServService,
        private route: ActivatedRoute,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServs();
    }

    load(id) {
        this.servService.find(id).subscribe((serv) => {
            this.serv = serv;
            this.principal.identity().then((account) => {
                if (account.login === this.serv.sm.user) {
                    this.isManager = true;
                } else {
                    this.isManager = false;
                }
            });
        });
    }

    becomeAnAgent() {
       /* this.servService.createAgent(this.serv);*/
        this.subscribeToSaveResponse(
            this.servService.createAgent(this.serv), true);
    }

    private subscribeToSaveResponse(result: Observable<Serv>, isCreated: boolean) {
        result.subscribe((res: Serv) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Serv, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.serv.created'
                : 'wishApp.serv.updated',
            { param : result.name }, null);

        this.eventManager.broadcast({ name: 'servListModification', content: 'OK'});
        this.isSaving = false;
        this.previousState();
        /*this.activeModal.dismiss(result);*/
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

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'servListModification',
            (response) => this.load(this.serv.id)
        );
    }

    /*clear() {
        this.activeModal.dismiss('cancel');
    }*/
}
