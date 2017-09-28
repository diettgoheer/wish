import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Work } from './work.model';
import { WorkService } from './work.service';
import {Principal} from '../../shared/auth/principal.service';

@Component({
    selector: 'jhi-work-detail',
    templateUrl: './work-detail.component.html'
})
export class WorkDetailComponent implements OnInit, OnDestroy {

    work: Work;
    success: any;
    error: any;
    isStarted: boolean;
    isInProgress: boolean;
    isFinished: boolean;
    isManager: boolean;
    isSponsor: boolean;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private workService: WorkService,
        private route: ActivatedRoute,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWorks();
    }

    load(id) {
        this.workService.find(id).subscribe((work) => {
            this.work = work;
            this.principal.identity().then((account) => {
                if (account.login === this.work.wm.user) {
                    this.isManager = true;
                }
                if (account.login === this.work.ws.user) {
                    this.isSponsor = true;
                }
                this.checkStatus(this.work);
            });

        });
    }

    checkStatus(work) {
        if (work.status === '待处理' && this.isManager) {
            this.isStarted = true;
        } else {
            this.isStarted = false;
        }

        if (work.status === '处理中' && this.isSponsor) {
            this.isInProgress = true;
        } else {
            this.isInProgress = false;
        }

        if (work.status === '已完成' && this.isSponsor) {
            this.isFinished = true;
        } else {
            this.isFinished = false;
        }
    }

    previousState() {
        window.history.back();
    }

    setStatus(status) {
        this.work.status = status;
        if (status === '已完成(已结账)') {
            this.workService.finish(this.work).subscribe((work) => {
                this.load(work.id);
            });
        } else {
            this.workService.update(this.work).subscribe((work) => {
                this.load(work.id);
            });
        }

    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWorks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'workListModification',
            (response) => this.load(this.work.id)
        );
    }
}
