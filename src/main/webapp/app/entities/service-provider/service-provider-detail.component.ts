import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { ServiceProvider } from './service-provider.model';
import { ServiceProviderService } from './service-provider.service';

@Component({
    selector: 'jhi-service-provider-detail',
    templateUrl: './service-provider-detail.component.html'
})
export class ServiceProviderDetailComponent implements OnInit, OnDestroy {

    serviceProvider: ServiceProvider;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private serviceProviderService: ServiceProviderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServiceProviders();
    }

    load(id) {
        this.serviceProviderService.find(id).subscribe((serviceProvider) => {
            this.serviceProvider = serviceProvider;
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

    registerChangeInServiceProviders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'serviceProviderListModification',
            (response) => this.load(this.serviceProvider.id)
        );
    }
}
