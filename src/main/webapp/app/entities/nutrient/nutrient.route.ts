import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Nutrient } from 'app/shared/model/nutrient.model';
import { NutrientService } from './nutrient.service';
import { NutrientComponent } from './nutrient.component';
import { NutrientDetailComponent } from './nutrient-detail.component';
import { NutrientUpdateComponent } from './nutrient-update.component';
import { NutrientDeletePopupComponent } from './nutrient-delete-dialog.component';
import { INutrient } from 'app/shared/model/nutrient.model';

@Injectable({ providedIn: 'root' })
export class NutrientResolve implements Resolve<INutrient> {
    constructor(private service: NutrientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Nutrient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Nutrient>) => response.ok),
                map((nutrient: HttpResponse<Nutrient>) => nutrient.body)
            );
        }
        return of(new Nutrient());
    }
}

export const nutrientRoute: Routes = [
    {
        path: 'nutrient',
        component: NutrientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Nutrients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nutrient/:id/view',
        component: NutrientDetailComponent,
        resolve: {
            nutrient: NutrientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Nutrients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nutrient/new',
        component: NutrientUpdateComponent,
        resolve: {
            nutrient: NutrientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Nutrients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'nutrient/:id/edit',
        component: NutrientUpdateComponent,
        resolve: {
            nutrient: NutrientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Nutrients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nutrientPopupRoute: Routes = [
    {
        path: 'nutrient/:id/delete',
        component: NutrientDeletePopupComponent,
        resolve: {
            nutrient: NutrientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Nutrients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
