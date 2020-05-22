import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';

@Component({
  templateUrl: './feature-delete-dialog.component.html',
})
export class FeatureDeleteDialogComponent {
  feature?: IFeature;

  constructor(protected featureService: FeatureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.featureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('featureListModification');
      this.activeModal.close();
    });
  }
}
