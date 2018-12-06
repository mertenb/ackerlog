import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AckerlogFieldModule } from './field/field.module';
import { AckerlogTaskModule } from './task/task.module';
import { AckerlogNutrientModule } from './nutrient/nutrient.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AckerlogFieldModule,
        AckerlogTaskModule,
        AckerlogNutrientModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AckerlogEntityModule {}
