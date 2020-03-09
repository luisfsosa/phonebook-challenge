import { NgModule } from '@angular/core';
import { PhonebookSharedLibsModule } from './shared-libs.module';
import { AlertErrorComponent } from './alert/alert-error.component';

@NgModule({
  imports: [PhonebookSharedLibsModule],
  declarations: [AlertErrorComponent],
  entryComponents: [],
  exports: [PhonebookSharedLibsModule, AlertErrorComponent]
})
export class PhonebookSharedModule {}
