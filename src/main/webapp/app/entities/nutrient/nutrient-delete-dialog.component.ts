import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INutrient } from 'app/shared/model/nutrient.model';
import { NutrientService } from './nutrient.service';

@Component({
    selector: 'jhi-nutrient-delete-dialog',
    templateUrl: './nutrient-delete-dialog.component.html'
})
export class NutrientDeleteDialogComponent {
    nutrient: INutrient;

    constructor(private nutrientService: NutrientService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nutrientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nutrientListModification',
                content: 'Deleted an nutrient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nutrient-delete-popup',
    template: ''
})
export class NutrientDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nutrient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NutrientDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.nutrient = nutrient;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
