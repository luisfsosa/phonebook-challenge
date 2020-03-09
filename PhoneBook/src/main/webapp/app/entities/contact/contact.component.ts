import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IContact } from 'app/shared/model/contact.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContactService } from './contact.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html'
})
export class ContactComponent implements OnInit, OnDestroy {
  contacts?: IContact[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  cols: any[] = [];
  searchValue = '';
  isFiltered = false;

  constructor(
    protected contactService: ContactService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.contactService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IContact[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.cols = [
      { field: 'id', header: 'ID' },
      { field: 'firstName', header: 'First Name' },
      { field: 'lastName', header: 'Last Name' },
      { field: 'phone', header: 'Phone' }
    ];

    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInContacts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContact): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContacts(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactListModification', () => this.loadPage());
  }

  delete(contact: IContact): void {
    Swal.fire({
      title: 'Confirm delete operation',
      text: 'Are you sure you want to delete this Contact?',
      icon: 'warning',
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonColor: '#6c757d',
      cancelButtonColor: '#dc3545',
      confirmButtonText: 'Cancel',
      cancelButtonText: 'Delete'
    }).then(result => {
      if (!result.value) {
        this.contactService.delete(contact.id).subscribe(() => {
          Swal.fire('Contacts', 'Your contact has been deleted.', 'success');
          this.loadPage();
        });
      }
    });
  }

  search(): void {
    const pageToLoad: number = this.page;
    this.contactService.findByAny(this.searchValue).subscribe(
      (res: HttpResponse<IContact[]>) => {
        this.isFiltered = true;
        this.onSuccess(res.body, res.headers, pageToLoad);
      },
      () => this.onError()
    );
  }

  clear(): void {
    this.isFiltered = false;
    this.searchValue = '';
    this.loadPage();
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IContact[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/contact'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.contacts = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
