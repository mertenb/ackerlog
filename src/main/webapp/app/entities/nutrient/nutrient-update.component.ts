import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INutrient } from 'app/shared/model/nutrient.model';
import { NutrientService } from './nutrient.service';
import { IUser, UserService } from 'app/core';
import { IField } from 'app/shared/model/field.model';
import { FieldService } from 'app/entities/field';

@Component({
    selector: 'jhi-nutrient-update',
    templateUrl: './nutrient-update.component.html'
})
export class NutrientUpdateComponent implements OnInit {
    nutrient: INutrient;
    isSaving: boolean;

    users: IUser[];

    fields: IField[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private nutrientService: NutrientService,
        private userService: UserService,
        private fieldService: FieldService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nutrient }) => {
            this.nutrient = nutrient;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.fieldService.query().subscribe(
            (res: HttpResponse<IField[]>) => {
                this.fields = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.nutrient.id !== undefined) {
            this.subscribeToSaveResponse(this.nutrientService.update(this.nutrient));
        } else {
            this.subscribeToSaveResponse(this.nutrientService.create(this.nutrient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INutrient>>) {
        result.subscribe((res: HttpResponse<INutrient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackFieldById(index: number, item: IField) {
        return item.id;
    }
}
