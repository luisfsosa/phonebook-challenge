import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PhonebookSharedModule } from 'app/shared/shared.module';
import { ContactComponent } from './contact.component';
import { ContactDetailComponent } from './contact-detail.component';
import { ContactUpdateComponent } from './contact-update.component';
import { contactRoute } from './contact.route';

@NgModule({
  imports: [PhonebookSharedModule, RouterModule.forChild(contactRoute)],
  declarations: [ContactComponent, ContactDetailComponent, ContactUpdateComponent],
  entryComponents: []
})
export class PhonebookContactModule {}
