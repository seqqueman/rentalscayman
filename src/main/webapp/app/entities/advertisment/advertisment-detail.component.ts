import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdvertisment } from 'app/shared/model/advertisment.model';

@Component({
  selector: 'jhi-advertisment-detail',
  templateUrl: './advertisment-detail.component.html',
})
export class AdvertismentDetailComponent implements OnInit {
  advertisment: IAdvertisment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advertisment }) => (this.advertisment = advertisment));
  }

  previousState(): void {
    window.history.back();
  }
}
