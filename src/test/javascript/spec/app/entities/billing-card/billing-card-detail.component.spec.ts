import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BillingCardDetailComponent } from '../../../../../../main/webapp/app/entities/billing-card/billing-card-detail.component';
import { BillingCardService } from '../../../../../../main/webapp/app/entities/billing-card/billing-card.service';
import { BillingCard } from '../../../../../../main/webapp/app/entities/billing-card/billing-card.model';

describe('Component Tests', () => {

    describe('BillingCard Management Detail Component', () => {
        let comp: BillingCardDetailComponent;
        let fixture: ComponentFixture<BillingCardDetailComponent>;
        let service: BillingCardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WishTestModule],
                declarations: [BillingCardDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BillingCardService,
                    JhiEventManager
                ]
            }).overrideTemplate(BillingCardDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BillingCardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingCardService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BillingCard(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.billingCard).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
