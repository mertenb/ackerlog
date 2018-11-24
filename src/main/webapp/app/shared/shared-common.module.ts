import { NgModule } from '@angular/core';

import { AckerlogSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AckerlogSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AckerlogSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AckerlogSharedCommonModule {}
