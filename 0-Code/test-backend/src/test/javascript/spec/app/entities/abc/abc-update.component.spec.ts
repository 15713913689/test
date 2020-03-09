/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { AbcUpdateComponent } from 'app/entities/abc/abc-update.component';
import { AbcService } from 'app/entities/abc/abc.service';
import { Abc } from 'app/shared/model/abc.model';

describe('Component Tests', () => {
    describe('Abc Management Update Component', () => {
        let comp: AbcUpdateComponent;
        let fixture: ComponentFixture<AbcUpdateComponent>;
        let service: AbcService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [AbcUpdateComponent]
            })
                .overrideTemplate(AbcUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AbcUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbcService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Abc(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.abc = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Abc();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.abc = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
