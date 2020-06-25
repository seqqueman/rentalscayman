import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdvertisment } from 'app/shared/model/advertisment.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AdvertismentService } from './advertisment.service';
import { AdvertismentDeleteDialogComponent } from './advertisment-delete-dialog.component';

@Component({
  selector: 'jhi-advertisment',
  templateUrl: './advertisment.component.html',
})
export class AdvertismentComponent implements OnInit, OnDestroy {
  advertisments?: IAdvertisment[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  querySearch = '';

  constructor(
    protected advertismentService: AdvertismentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.advertismentService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        search: this.querySearch,
      })
      .subscribe(
        (res: HttpResponse<IAdvertisment[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe(queryParams => {
      this.querySearch = queryParams.get('search')!;
    });
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams?.page ? data.pagingParams.page : 1;
      this.ascending = data.pagingParams?.ascending ? data.pagingParams.ascending : false;
      this.predicate = data.pagingParams?.predicate ? data.pagingParams.predicate : 'createAt';
      this.ngbPaginationPage = data.pagingParams?.page ? data.pagingParams.page : 1;
      this.loadPage();
    });
    this.handleBackNavigation();
    this.registerChangeInAdvertisments();
  }

  handleBackNavigation(): void {
    this.activatedRoute.queryParamMap.subscribe((params: ParamMap) => {
      const prevPage = params.get('page');
      const prevSort = params.get('sort');
      const prevSortSplit = prevSort?.split(',');
      if (prevSortSplit) {
        this.predicate = prevSortSplit[0];
        this.ascending = prevSortSplit[1] === 'asc';
      }
      if (prevPage && +prevPage !== this.page) {
        this.ngbPaginationPage = +prevPage;
        this.loadPage(+prevPage);
      } else {
        this.loadPage(this.page);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdvertisment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAdvertisments(): void {
    this.eventSubscriber = this.eventManager.subscribe('advertismentListModification', () => this.loadPage());
  }

  delete(advertisment: IAdvertisment): void {
    const modalRef = this.modalService.open(AdvertismentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.advertisment = advertisment;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'createAt') {
      result.push('createAt');
    }
    return result;
  }

  protected onSuccess(data: IAdvertisment[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    // this.router.navigate(['/advertisment'], {
    // queryParams: {
    // page: this.page,
    // size: this.itemsPerPage,
    // sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
    // },
    // });
    this.advertisments = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
