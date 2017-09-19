import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ServDetailComponent } from '../../../../../../main/webapp/app/entities/serv/serv-detail.component';
import { ServService } from '../../../../../../main/webapp/app/entities/serv/serv.service';
import { Serv } from '../../../../../../main/webapp/app/entities/serv/serv.model';

describe('Component Tests', () => {

    describe('Serv Management Detail Component', () => {
        let comp: ServDetailComponent;
        let fixture: ComponentFixture<ServDetailComponent>;
        let service: ServService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WishTestModule],
                declarations: [ServDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ServService,
                    JhiEventManager
                ]
            }).overrideTemplate(ServDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Serv(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.serv).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
