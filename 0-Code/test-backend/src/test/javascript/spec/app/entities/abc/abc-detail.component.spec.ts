/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { AbcDetailComponent } from 'app/entities/abc/abc-detail.component';
import { Abc } from 'app/shared/model/abc.model';

describe('Component Tests', () => {
    describe('Abc Management Detail Component', () => {
        let comp: AbcDetailComponent;
        let fixture: ComponentFixture<AbcDetailComponent>;
        const route = ({ data: of({ abc: new Abc(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [AbcDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AbcDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AbcDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.abc).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
