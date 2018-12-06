/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AckerlogTestModule } from '../../../test.module';
import { NutrientDeleteDialogComponent } from 'app/entities/nutrient/nutrient-delete-dialog.component';
import { NutrientService } from 'app/entities/nutrient/nutrient.service';

describe('Component Tests', () => {
    describe('Nutrient Management Delete Component', () => {
        let comp: NutrientDeleteDialogComponent;
        let fixture: ComponentFixture<NutrientDeleteDialogComponent>;
        let service: NutrientService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AckerlogTestModule],
                declarations: [NutrientDeleteDialogComponent]
            })
                .overrideTemplate(NutrientDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NutrientDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NutrientService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
