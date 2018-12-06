import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INutrient } from 'app/shared/model/nutrient.model';

type EntityResponseType = HttpResponse<INutrient>;
type EntityArrayResponseType = HttpResponse<INutrient[]>;

@Injectable({ providedIn: 'root' })
export class NutrientService {
    public resourceUrl = SERVER_API_URL + 'api/nutrients';

    constructor(private http: HttpClient) {}

    create(nutrient: INutrient): Observable<EntityResponseType> {
        return this.http.post<INutrient>(this.resourceUrl, nutrient, { observe: 'response' });
    }

    update(nutrient: INutrient): Observable<EntityResponseType> {
        return this.http.put<INutrient>(this.resourceUrl, nutrient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INutrient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INutrient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
