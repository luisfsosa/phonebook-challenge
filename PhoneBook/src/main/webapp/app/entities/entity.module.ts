import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.PhonebookContactModule)
      }
    ])
  ]
})
export class PhonebookEntityModule {}
