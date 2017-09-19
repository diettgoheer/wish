import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Work } from './work.model';
import { WorkService } from './work.service';

@Injectable()
export class WorkPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private workService: WorkService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.workService.find(id).subscribe((work) => {
                if (work.startDate) {
                    work.startDate = {
                        year: work.startDate.getFullYear(),
                        month: work.startDate.getMonth() + 1,
                        day: work.startDate.getDate()
                    };
                }
                if (work.endDate) {
                    work.endDate = {
                        year: work.endDate.getFullYear(),
                        month: work.endDate.getMonth() + 1,
                        day: work.endDate.getDate()
                    };
                }
                work.createdTime = this.datePipe
                    .transform(work.createdTime, 'yyyy-MM-ddThh:mm');
                work.updatedTime = this.datePipe
                    .transform(work.updatedTime, 'yyyy-MM-ddThh:mm');
                this.workModalRef(component, work);
            });
        } else {
            return this.workModalRef(component, new Work());
        }
    }

    workModalRef(component: Component, work: Work): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.work = work;
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
