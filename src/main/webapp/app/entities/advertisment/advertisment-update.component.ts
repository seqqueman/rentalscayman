import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
// import { map } from 'rxjs/operators';

import { IAdvertisment, Advertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from './advertisment.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IFeature } from 'app/shared/model/feature.model';
import { FeatureService } from 'app/entities/feature/feature.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { PropertyType } from 'app/shared/model/enumerations/property-type.model';
import { AreaDisctrict } from 'app/shared/model/enumerations/area-disctrict.model';
import { TypeAdvertisment } from 'app/shared/model/enumerations/type-advertisment.model';
import { ViaType } from 'app/shared/model/enumerations/via-type.model';

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
  // createAtDp: any;
  // modifiedAtDp: any;

  ePropertyType = PropertyType;
  eAreaDistrict = AreaDisctrict;
  eTypeAdvertis = TypeAdvertisment;
  eViaType = ViaType;

  editForm: FormGroup;

  constructor(
    protected advertismentService: AdvertismentService,
    protected addressService: AddressService,
    protected featureService: FeatureService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.editForm = fb.group({
      id: [],
      description: [null, [Validators.required]],
      // createAt: [null, [Validators.required]],
      // modifiedAt: [null, [Validators.required]],
      typeAd: [null, [Validators.required]],
      propertyType: [null, [Validators.required]],
      // active: [null, [Validators.required]],
      price: [null, [Validators.required]],
      // reference: [''],
      address: this.fb.group({
        typeOfVia: [null, [Validators.required]],
        number: [null, [Validators.required]],
        zipCode: [null, [Validators.required]],
        areaDisctrict: [null, [Validators.required]],
      }),
      feature: this.fb.group({
        numberBedrooms: [null, [Validators.required]],
        numberBathroom: [null, [Validators.required]],
        fullKitchen: [null, [Validators.required]],
        elevator: [],
        parking: [],
        airConditionair: [],
        backyard: [],
        pool: [],
        m2: [],
      }),
      user: [],
    });
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advertisment }) => {
      this.updateForm(advertisment);

      // this.addressService
      // .query({ filter: 'advertisment-is-null' })
      // .pipe(
      // map((res: HttpResponse<IAddress[]>) => {
      // return res.body || [];
      // })
      // )
      // .subscribe((resBody: IAddress[]) => {
      // if (!advertisment.address || !advertisment.address.id) {
      // this.addresses = resBody;
      // } else {
      // this.addressService
      // .find(advertisment.address.id)
      // .pipe(
      // map((subRes: HttpResponse<IAddress>) => {
      // return subRes.body ? [subRes.body].concat(resBody) : resBody;
      // })
      // )
      // .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
      // }
      // });
      //
      // this.featureService
      // .query({ filter: 'advertisment-is-null' })
      // .pipe(
      // map((res: HttpResponse<IFeature[]>) => {
      // return res.body || [];
      // })
      // )
      // .subscribe((resBody: IFeature[]) => {
      // if (!advertisment.feature || !advertisment.feature.id) {
      // this.features = resBody;
      // } else {
      // this.featureService
      // .find(advertisment.feature.id)
      // .pipe(
      // map((subRes: HttpResponse<IFeature>) => {
      // return subRes.body ? [subRes.body].concat(resBody) : resBody;
      // })
      // )
      // .subscribe((concatRes: IFeature[]) => (this.features = concatRes));
      // }
      // });
      //
      // this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(advertisment: IAdvertisment): void {
    this.editForm.patchValue({
      id: advertisment.id,
      description: advertisment.description,
      // createAt: advertisment.createAt,
      // modifiedAt: advertisment.modifiedAt,
      typeAd: advertisment.typeAd,
      propertyType: advertisment.propertyType,
      // active: advertisment.active,
      price: advertisment.price,
      // reference: advertisment.reference,
      address: {
        typeOfVia: advertisment.address?.typeOfVia,
        number: advertisment.address?.number,
        zipCode: advertisment.address?.zipCode,
        areaDisctrict: advertisment.address?.areaDisctrict,
      },
      feature: {
        numberBedrooms: advertisment.feature?.numberBedrooms,
        numberBathroom: advertisment.feature?.numberBathroom,
        fullKitchen: advertisment.feature?.fullKitchen,
        elevator: advertisment.feature?.elevator,
        parking: advertisment.feature?.parking,
        airConditionair: advertisment.feature?.airConditionair,
        backyard: advertisment.feature?.backyard,
        pool: advertisment.feature?.pool,
        m2: advertisment.feature?.m2,
      },

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
      // createAt: this.editForm.get(['createAt'])!.value,
      // modifiedAt: this.editForm.get(['modifiedAt'])!.value,
      typeAd: this.editForm.get(['typeAd'])!.value,
      propertyType: this.editForm.get(['propertyType'])!.value,
      // active: this.editForm.get(['active'])!.value,
      price: this.editForm.get(['price'])!.value,
      // reference: this.editForm.get(['reference'])!.value,
      address: {
        typeOfVia: this.editForm.get(['address', 'typeOfVia'])!.value,
        number: this.editForm.get(['address', 'number'])!.value,
        zipCode: this.editForm.get(['address', 'zipCode'])!.value,
        areaDisctrict: this.editForm.get(['address', 'areaDisctrict'])!.value,
      },
      feature: {
        numberBedrooms: this.editForm.get(['feature', 'numberBedrooms'])!.value,
        numberBathroom: this.editForm.get(['feature', 'numberBathroom'])!.value,
        fullKitchen: this.editForm.get(['feature', 'fullKitchen'])!.value,
        elevator: this.editForm.get(['feature', 'elevator'])!.value,
        parking: this.editForm.get(['feature', 'parking'])!.value,
        airConditionair: this.editForm.get(['feature', 'airConditionair'])!.value,
        backyard: this.editForm.get(['feature', 'backyard'])!.value,
        pool: this.editForm.get(['feature', 'pool'])!.value,
        m2: this.editForm.get(['feature', 'm2'])!.value,
      },
      // this.editForm.get(['feature'])!.value,
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
