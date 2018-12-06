import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ITask } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { INutrient } from 'app/shared/model/nutrient.model';
import { NutrientService } from 'app/entities/nutrient';
import { IUser, UserService } from 'app/core';
import { IField } from 'app/shared/model/field.model';
import { FieldService } from 'app/entities/field';

@Component({
    selector: 'jhi-task-update',
    templateUrl: './task-update.component.html'
})
export class TaskUpdateComponent implements OnInit {
    task: ITask;
    isSaving: boolean;

    nutrients: INutrient[];

    users: IUser[];

    fields: IField[];
    actionDateDp: any;
    entryDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private taskService: TaskService,
        private nutrientService: NutrientService,
        private userService: UserService,
        private fieldService: FieldService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ task }) => {
            this.task = task;
        });
        this.nutrientService.query({ filter: 'task-is-null' }).subscribe(
            (res: HttpResponse<INutrient[]>) => {
                if (!this.task.nutrient || !this.task.nutrient.id) {
                    this.nutrients = res.body;
                } else {
                    this.nutrientService.find(this.task.nutrient.id).subscribe(
                        (subRes: HttpResponse<INutrient>) => {
                            this.nutrients = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.task.id !== undefined) {
            this.subscribeToSaveResponse(this.taskService.update(this.task));
        } else {
            this.subscribeToSaveResponse(this.taskService.create(this.task));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>) {
        result.subscribe((res: HttpResponse<ITask>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackNutrientById(index: number, item: INutrient) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackFieldById(index: number, item: IField) {
        return item.id;
    }
}
