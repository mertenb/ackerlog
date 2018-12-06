import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AckerlogSharedModule } from 'app/shared';
import { AckerlogAdminModule } from 'app/admin/admin.module';
import {
    FieldComponent,
    FieldDetailComponent,
    FieldUpdateComponent,
    FieldDeletePopupComponent,
    FieldDeleteDialogComponent,
    fieldRoute,
    fieldPopupRoute
} from './';

const ENTITY_STATES = [...fieldRoute, ...fieldPopupRoute];

@NgModule({
    imports: [AckerlogSharedModule, AckerlogAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FieldComponent, FieldDetailComponent, FieldUpdateComponent, FieldDeleteDialogComponent, FieldDeletePopupComponent],
    entryComponents: [FieldComponent, FieldUpdateComponent, FieldDeleteDialogComponent, FieldDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AckerlogFieldModule {}
