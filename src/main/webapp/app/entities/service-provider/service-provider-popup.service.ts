import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ServiceProvider } from './service-provider.model';
import { ServiceProviderService } from './service-provider.service';

@Injectable()
export class ServiceProviderPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private serviceProviderService: ServiceProviderService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.serviceProviderService.find(id).subscribe((serviceProvider) => {
                if (serviceProvider.enterpriseCreatedTime) {
                    serviceProvider.enterpriseCreatedTime = {
                        year: serviceProvider.enterpriseCreatedTime.getFullYear(),
                        month: serviceProvider.enterpriseCreatedTime.getMonth() + 1,
                        day: serviceProvider.enterpriseCreatedTime.getDate()
                    };
                }
                if (serviceProvider.certificationTime) {
                    serviceProvider.certificationTime = {
                        year: serviceProvider.certificationTime.getFullYear(),
                        month: serviceProvider.certificationTime.getMonth() + 1,
                        day: serviceProvider.certificationTime.getDate()
                    };
                }
                this.serviceProviderModalRef(component, serviceProvider);
            });
        } else {
            return this.serviceProviderModalRef(component, new ServiceProvider());
        }
    }

    serviceProviderModalRef(component: Component, serviceProvider: ServiceProvider): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.serviceProvider = serviceProvider;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
