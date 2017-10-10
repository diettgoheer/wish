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
import {Principal} from '../../shared/auth/principal.service';
import {CircleService} from '../circle/circle.service';
import {Circle} from '../circle/circle.model';
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'jhi-person-detail',
    templateUrl: './person-detail.component.html'
})
export class PersonDetailComponent implements OnInit, OnDestroy {

    person: Person;
    circle: Circle;
    people: Person[];
    works: Work[];
    servs: Serv[];
    projects: Project[];
    hasWorks: Boolean;
    hasServs: Boolean;
    hasProjects: Boolean;
    account: any;
    isUser: Boolean;
    isFriend: Boolean;
    isSaving: Boolean;
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
        private circleService: CircleService,
        private route: ActivatedRoute,
        private principal: Principal
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
            this.loadAll(this.person.user);
        });
    }

    loadAll(login) {
        this.principal.identity().then((account) => {
            if (account.login === login) {
                this.isUser = true;
            } else {
                this.isUser = false;
            }
        });

        this.workService.queryByUserLogin(login).subscribe(
            (res: ResponseWrapper) => {
                this.works = res.json;
                if (this.works.length > 0) {
                    this.hasWorks = true;
                }
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.servService.queryByUserLogin(login).subscribe(
            (res: ResponseWrapper) => {
                this.servs = res.json;
                if (this.servs.length > 0) {
                    this.hasServs = true;
                }
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.projectService.queryByUserLogin(login).subscribe(
            (res: ResponseWrapper) => {
                this.projects = res.json;
                if (this.projects.length > 0) {
                    this.hasProjects = true;
                }
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.isFriend = false;
        this.personService.query().subscribe(
            (res: ResponseWrapper) => {
                this.people = res.json;
                for (let i = 0; i < this.people.length; i++) {
                    if (this.people.pop().user === login) {
                        this.isFriend = true;
                    }
                }
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

    addFriend() {
        this.isSaving = true;
        this.circle = new Circle;
        this.circle.friendLogin = this.person.user;
        this.subscribeToSaveResponse(
            this.circleService.create(this.circle), true);
    }

    private subscribeToSaveResponse(result: Observable<Circle>, isCreated: boolean) {
        result.subscribe((res: Circle) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Circle, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'wishApp.circle.created'
                : 'wishApp.circle.updated',
            { param : result.friendLogin }, null);

        this.eventManager.broadcast({ name: 'circleListModification', content: 'OK'});
        this.isSaving = false;
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
