import {Component, OnDestroy, OnInit} from '@angular/core';
import {PersonService} from '../../entities/person/person.service';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
import {Principal} from '../../shared/auth/principal.service';
import {Person} from '../../entities/person/person.model';
import {Subscription} from 'rxjs/Subscription';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';
import {Router} from '@angular/router';
import {ProfileService} from '../../layouts/profiles/profile.service';
import {LoginModalService} from '../../shared/login/login-modal.service';
import {LoginService} from '../../shared/login/login.service';

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
        private loginService: LoginService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router
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

    trackId(index: number, item: Person) {
        return item.id;
    }

    logout() {
       // this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }
}
