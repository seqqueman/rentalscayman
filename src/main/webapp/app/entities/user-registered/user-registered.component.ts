import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserRegistered } from 'app/shared/model/user-registered.model';
import { UserRegisteredService } from './user-registered.service';
import { UserRegisteredDeleteDialogComponent } from './user-registered-delete-dialog.component';

@Component({
  selector: 'jhi-user-registered',
  templateUrl: './user-registered.component.html',
})
export class UserRegisteredComponent implements OnInit, OnDestroy {
  userRegistereds?: IUserRegistered[];
  eventSubscriber?: Subscription;

  constructor(
    protected userRegisteredService: UserRegisteredService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userRegisteredService.query().subscribe((res: HttpResponse<IUserRegistered[]>) => (this.userRegistereds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserRegistereds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserRegistered): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserRegistereds(): void {
    this.eventSubscriber = this.eventManager.subscribe('userRegisteredListModification', () => this.loadAll());
  }

  delete(userRegistered: IUserRegistered): void {
    const modalRef = this.modalService.open(UserRegisteredDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userRegistered = userRegistered;
  }
}
