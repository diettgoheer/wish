import {Component, OnDestroy, OnInit} from '@angular/core';
import {PersonService} from '../../entities/person/person.service';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
import {Principal} from '../../shared/auth/principal.service';
import {Person} from '../../entities/person/person.model';
import {Subscription} from 'rxjs/Subscription';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';

@Component({
    selector: 'jhi-mine',
    templateUrl: './mine.component.html'
})
export class MineComponent implements OnInit, OnDestroy {
    people: Person[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private personService: PersonService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.loadAll();
        this.registerChangeInPeople();
    }
    loadAll() {

            this.personService.findHost().subscribe(
                (res: ResponseWrapper) => {
                    this.people = res.json;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeople() {
        this.eventSubscriber = this.eventManager.subscribe('personListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
