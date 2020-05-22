import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from './advertisment.service';

@Component({
  templateUrl: './advertisment-delete-dialog.component.html',
})
export class AdvertismentDeleteDialogComponent {
  advertisment?: IAdvertisment;

  constructor(
    protected advertismentService: AdvertismentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.advertismentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('advertismentListModification');
      this.activeModal.close();
    });
  }
}
