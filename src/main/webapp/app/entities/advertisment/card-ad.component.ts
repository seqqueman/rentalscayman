import { Component, OnInit, Input } from '@angular/core';
import { Advertisment } from '../../shared/model/advertisment.model';

@Component({
  selector: 'jhi-card-ad',
  templateUrl: './card-ad.component.html',
  styles: [],
})
export class CardAdComponent implements OnInit {
  @Input() advertisment: Advertisment = {};

  constructor() {}

  ngOnInit(): void {}
}
