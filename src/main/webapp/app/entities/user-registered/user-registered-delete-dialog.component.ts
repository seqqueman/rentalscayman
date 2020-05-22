import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserRegistered } from 'app/shared/model/user-registered.model';
import { UserRegisteredService } from './user-registered.service';

@Component({
  templateUrl: './user-registered-delete-dialog.component.html',
})
export class UserRegisteredDeleteDialogComponent {
  userRegistered?: IUserRegistered;

  constructor(
    protected userRegisteredService: UserRegisteredService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userRegisteredService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userRegisteredListModification');
      this.activeModal.close();
    });
  }
}
