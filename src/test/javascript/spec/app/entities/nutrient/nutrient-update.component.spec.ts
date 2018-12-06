/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AckerlogTestModule } from '../../../test.module';
import { NutrientUpdateComponent } from 'app/entities/nutrient/nutrient-update.component';
import { NutrientService } from 'app/entities/nutrient/nutrient.service';
import { Nutrient } from 'app/shared/model/nutrient.model';

describe('Component Tests', () => {
    describe('Nutrient Management Update Component', () => {
        let comp: NutrientUpdateComponent;
        let fixture: ComponentFixture<NutrientUpdateComponent>;
        let service: NutrientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AckerlogTestModule],
                declarations: [NutrientUpdateComponent]
            })
                .overrideTemplate(NutrientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NutrientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NutrientService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Nutrient(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.nutrient = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Nutrient();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.nutrient = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
