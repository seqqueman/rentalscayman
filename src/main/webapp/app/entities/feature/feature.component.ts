import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';
import { FeatureDeleteDialogComponent } from './feature-delete-dialog.component';

@Component({
  selector: 'jhi-feature',
  templateUrl: './feature.component.html',
})
export class FeatureComponent implements OnInit, OnDestroy {
  features?: IFeature[];
  eventSubscriber?: Subscription;

  constructor(protected featureService: FeatureService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.featureService.query().subscribe((res: HttpResponse<IFeature[]>) => (this.features = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFeatures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFeature): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFeatures(): void {
    this.eventSubscriber = this.eventManager.subscribe('featureListModification', () => this.loadAll());
  }

  delete(feature: IFeature): void {
    const modalRef = this.modalService.open(FeatureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feature = feature;
  }
}
