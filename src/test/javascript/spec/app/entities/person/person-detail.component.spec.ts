import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonDetailComponent } from '../../../../../../main/webapp/app/entities/person/person-detail.component';
import { PersonService } from '../../../../../../main/webapp/app/entities/person/person.service';
import { Person } from '../../../../../../main/webapp/app/entities/person/person.model';

describe('Component Tests', () => {

    describe('Person Management Detail Component', () => {
        let comp: PersonDetailComponent;
        let fixture: ComponentFixture<PersonDetailComponent>;
        let service: PersonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WishTestModule],
                declarations: [PersonDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonService,
                    JhiEventManager
                ]
            }).overrideTemplate(PersonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Person(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.person).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
