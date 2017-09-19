import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Serv } from './serv.model';
import { ServService } from './serv.service';

@Injectable()
export class ServPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private servService: ServService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.servService.find(id).subscribe((serv) => {
                serv.createdTime = this.datePipe
                    .transform(serv.createdTime, 'yyyy-MM-ddThh:mm');
                serv.updatedTime = this.datePipe
                    .transform(serv.updatedTime, 'yyyy-MM-ddThh:mm');
                this.servModalRef(component, serv);
            });
        } else {
            return this.servModalRef(component, new Serv());
        }
    }

    servModalRef(component: Component, serv: Serv): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.serv = serv;
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
