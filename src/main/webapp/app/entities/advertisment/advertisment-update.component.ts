import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAdvertisment, Advertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from './advertisment.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IFeature } from 'app/shared/model/feature.model';
import { FeatureService } from 'app/entities/feature/feature.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IAddress | IFeature | IUser;

@Component({
  selector: 'jhi-advertisment-update',
  templateUrl: './advertisment-update.component.html',
})
export class AdvertismentUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];
  features: IFeature[] = [];
  users: IUser[] = [];
  createAtDp: any;
  modifiedAtDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    createAt: [null, [Validators.required]],
    modifiedAt: [null, [Validators.required]],
    typeAd: [null, [Validators.required]],
    propertyType: [null, [Validators.required]],
    active: [null, [Validators.required]],
    price: [null, [Validators.required]],
    reference: [null, [Validators.required]],
    address: [],
    feature: [],
    user: [],
  });

  constructor(
    protected advertismentService: AdvertismentService,
    protected addressService: AddressService,
    protected featureService: FeatureService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advertisment }) => {
      this.updateForm(advertisment);

      this.addressService
        .query({ filter: 'advertisment-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!advertisment.address || !advertisment.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(advertisment.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });

      this.featureService
        .query({ filter: 'advertisment-is-null' })
        .pipe(
          map((res: HttpResponse<IFeature[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IFeature[]) => {
          if (!advertisment.feature || !advertisment.feature.id) {
            this.features = resBody;
          } else {
            this.featureService
              .find(advertisment.feature.id)
              .pipe(
                map((subRes: HttpResponse<IFeature>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IFeature[]) => (this.features = concatRes));
          }
        });

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(advertisment: IAdvertisment): void {
    this.editForm.patchValue({
      id: advertisment.id,
      description: advertisment.description,
      createAt: advertisment.createAt,
      modifiedAt: advertisment.modifiedAt,
      typeAd: advertisment.typeAd,
      propertyType: advertisment.propertyType,
      active: advertisment.active,
      price: advertisment.price,
      reference: advertisment.reference,
      address: advertisment.address,
      feature: advertisment.feature,
      user: advertisment.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const advertisment = this.createFromForm();
    if (advertisment.id !== undefined) {
      this.subscribeToSaveResponse(this.advertismentService.update(advertisment));
    } else {
      this.subscribeToSaveResponse(this.advertismentService.create(advertisment));
    }
  }

  private createFromForm(): IAdvertisment {
    return {
      ...new Advertisment(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createAt: this.editForm.get(['createAt'])!.value,
      modifiedAt: this.editForm.get(['modifiedAt'])!.value,
      typeAd: this.editForm.get(['typeAd'])!.value,
      propertyType: this.editForm.get(['propertyType'])!.value,
      active: this.editForm.get(['active'])!.value,
      price: this.editForm.get(['price'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      address: this.editForm.get(['address'])!.value,
      feature: this.editForm.get(['feature'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvertisment>>): void {
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
