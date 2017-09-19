import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ServiceProviderDetailComponent } from '../../../../../../main/webapp/app/entities/service-provider/service-provider-detail.component';
import { ServiceProviderService } from '../../../../../../main/webapp/app/entities/service-provider/service-provider.service';
import { ServiceProvider } from '../../../../../../main/webapp/app/entities/service-provider/service-provider.model';

describe('Component Tests', () => {

    describe('ServiceProvider Management Detail Component', () => {
        let comp: ServiceProviderDetailComponent;
        let fixture: ComponentFixture<ServiceProviderDetailComponent>;
        let service: ServiceProviderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WishTestModule],
                declarations: [ServiceProviderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ServiceProviderService,
                    JhiEventManager
                ]
            }).overrideTemplate(ServiceProviderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServiceProviderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServiceProviderService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ServiceProvider(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.serviceProvider).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
