import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';

import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContact, Contact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-contact-update',
  templateUrl: './contact-update.component.html'
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    phone: []
  });

  constructor(protected contactService: ContactService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      this.updateForm(contact);
    });
  }

  updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      firstName: contact.firstName,
      lastName: contact.lastName,
      phone: contact.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(test: boolean): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact), false, test);
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact), true, test);
    }
  }

  private createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>, created: boolean, test: boolean): void {
    result.subscribe(
      () => this.onSaveSuccess(created, test),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(created: boolean, test: boolean): void {
    this.isSaving = false;
    if (created && !test) {
      Swal.fire({
        title: 'Contacts',
        html: 'Your contact has been created.',
        icon: 'success',
        timer: 1000
      });
    } else if (!test) {
      Swal.fire({
        title: 'Contacts',
        html: 'Your contact has been updated.',
        icon: 'success',
        timer: 1000
      });
    }
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
