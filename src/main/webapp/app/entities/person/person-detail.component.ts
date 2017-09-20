import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {JhiEventManager, JhiDataUtils, JhiAlertService} from 'ng-jhipster';

import { Person } from './person.model';
import { PersonService } from './person.service';
import {WorkService} from '../work/work.service';
import {ProjectService} from '../project/project.service';
import {ServService} from '../serv/serv.service';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';
import {Work} from '../work/work.model';
import {Serv} from '../serv/serv.model';
import {Project} from '../project/project.model';

@Component({
    selector: 'jhi-person-detail',
    templateUrl: './person-detail.component.html'
})
export class PersonDetailComponent implements OnInit, OnDestroy {

    person: Person;
    works: Work[];
    servs: Serv[];
    projects: Project[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private personService: PersonService,
        private workService: WorkService,
        private projectService: ProjectService,
        private servService: ServService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeople();
    }

    load(id) {
        this.personService.find(id).subscribe((person) => {
            this.person = person;
            this.loadAll(person.user);
        });
    }

    loadAll(login) {
        this.workService.queryByUserLogin(login).subscribe(
            (res: ResponseWrapper) => {
                this.works = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.servService.queryByUserLogin(login).subscribe(
            (res: ResponseWrapper) => {
                this.servs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.projectService.queryByUserLogin(login).subscribe(
            (res: ResponseWrapper) => {
                this.projects = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
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

    registerChangeInPeople() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personListModification',
            (response) => this.load(this.person.id)
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
