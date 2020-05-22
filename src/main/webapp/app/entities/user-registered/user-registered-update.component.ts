import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserRegistered, UserRegistered } from 'app/shared/model/user-registered.model';
import { UserRegisteredService } from './user-registered.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

type SelectableEntity = IUser | ICompany;

@Component({
  selector: 'jhi-user-registered-update',
  templateUrl: './user-registered-update.component.html',
})
export class UserRegisteredUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    advertiser: [],
    phone: [],
    numberOfAds: [],
    user: [],
    company: [],
  });

  constructor(
    protected userRegisteredService: UserRegisteredService,
    protected userService: UserService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistered }) => {
      this.updateForm(userRegistered);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.companyService
        .query({ filter: 'userregistered-is-null' })
        .pipe(
          map((res: HttpResponse<ICompany[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompany[]) => {
          if (!userRegistered.company || !userRegistered.company.id) {
            this.companies = resBody;
          } else {
            this.companyService
              .find(userRegistered.company.id)
              .pipe(
                map((subRes: HttpResponse<ICompany>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompany[]) => (this.companies = concatRes));
          }
        });
    });
  }

  updateForm(userRegistered: IUserRegistered): void {
    this.editForm.patchValue({
      id: userRegistered.id,
      advertiser: userRegistered.advertiser,
      phone: userRegistered.phone,
      numberOfAds: userRegistered.numberOfAds,
      user: userRegistered.user,
      company: userRegistered.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userRegistered = this.createFromForm();
    if (userRegistered.id !== undefined) {
      this.subscribeToSaveResponse(this.userRegisteredService.update(userRegistered));
    } else {
      this.subscribeToSaveResponse(this.userRegisteredService.create(userRegistered));
    }
  }

  private createFromForm(): IUserRegistered {
    return {
      ...new UserRegistered(),
      id: this.editForm.get(['id'])!.value,
      advertiser: this.editForm.get(['advertiser'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      numberOfAds: this.editForm.get(['numberOfAds'])!.value,
      user: this.editForm.get(['user'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserRegistered>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
