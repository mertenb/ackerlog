import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITask } from 'app/shared/model/task.model';

type EntityResponseType = HttpResponse<ITask>;
type EntityArrayResponseType = HttpResponse<ITask[]>;

@Injectable({ providedIn: 'root' })
export class TaskService {
    public resourceUrl = SERVER_API_URL + 'api/tasks';

    constructor(private http: HttpClient) {}

    create(task: ITask): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(task);
        return this.http
            .post<ITask>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(task: ITask): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(task);
        return this.http
            .put<ITask>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITask>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITask[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(task: ITask): ITask {
        const copy: ITask = Object.assign({}, task, {
            actionDate: task.actionDate != null && task.actionDate.isValid() ? task.actionDate.format(DATE_FORMAT) : null,
            entryDate: task.entryDate != null && task.entryDate.isValid() ? task.entryDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.actionDate = res.body.actionDate != null ? moment(res.body.actionDate) : null;
            res.body.entryDate = res.body.entryDate != null ? moment(res.body.entryDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((task: ITask) => {
                task.actionDate = task.actionDate != null ? moment(task.actionDate) : null;
                task.entryDate = task.entryDate != null ? moment(task.entryDate) : null;
            });
        }
        return res;
    }
}
