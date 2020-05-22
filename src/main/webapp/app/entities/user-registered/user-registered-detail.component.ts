import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserRegistered } from 'app/shared/model/user-registered.model';

@Component({
  selector: 'jhi-user-registered-detail',
  templateUrl: './user-registered-detail.component.html',
})
export class UserRegisteredDetailComponent implements OnInit {
  userRegistered: IUserRegistered | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistered }) => (this.userRegistered = userRegistered));
  }

  previousState(): void {
    window.history.back();
  }
}
