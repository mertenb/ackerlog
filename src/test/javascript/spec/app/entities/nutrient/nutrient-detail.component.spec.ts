/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AckerlogTestModule } from '../../../test.module';
import { NutrientDetailComponent } from 'app/entities/nutrient/nutrient-detail.component';
import { Nutrient } from 'app/shared/model/nutrient.model';

describe('Component Tests', () => {
    describe('Nutrient Management Detail Component', () => {
        let comp: NutrientDetailComponent;
        let fixture: ComponentFixture<NutrientDetailComponent>;
        const route = ({ data: of({ nutrient: new Nutrient(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AckerlogTestModule],
                declarations: [NutrientDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NutrientDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NutrientDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.nutrient).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
