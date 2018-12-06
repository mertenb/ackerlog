import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AckerlogSharedModule } from 'app/shared';
import { AckerlogAdminModule } from 'app/admin/admin.module';
import {
    NutrientComponent,
    NutrientDetailComponent,
    NutrientUpdateComponent,
    NutrientDeletePopupComponent,
    NutrientDeleteDialogComponent,
    nutrientRoute,
    nutrientPopupRoute
} from './';

const ENTITY_STATES = [...nutrientRoute, ...nutrientPopupRoute];

@NgModule({
    imports: [AckerlogSharedModule, AckerlogAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NutrientComponent,
        NutrientDetailComponent,
        NutrientUpdateComponent,
        NutrientDeleteDialogComponent,
        NutrientDeletePopupComponent
    ],
    entryComponents: [NutrientComponent, NutrientUpdateComponent, NutrientDeleteDialogComponent, NutrientDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AckerlogNutrientModule {}
