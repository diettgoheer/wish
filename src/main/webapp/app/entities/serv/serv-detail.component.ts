import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Serv } from './serv.model';
import { ServService } from './serv.service';
import {Principal} from '../../shared/auth/principal.service';

@Component({
    selector: 'jhi-serv-detail',
    templateUrl: './serv-detail.component.html'
})
export class ServDetailComponent implements OnInit, OnDestroy {

    isManager: Boolean;
    serv: Serv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
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
        });
        this.principal.identity().then((account) => {
            if (account.login === this.serv.sm.user) {
                this.isManager = true;
            } else {
                this.isManager = false;
            }
        });
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
}
