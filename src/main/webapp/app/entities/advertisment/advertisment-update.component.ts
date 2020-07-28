/* eslint-disable no-console */
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
import { CurrencyPipe } from '@angular/common';

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
  hidePrice = 0;
  // createAtDp: any;
  // modifiedAtDp: any;

  ePropertyType = PropertyType;
  eAreaDistrict = AreaDisctrict;
  eTypeAdvertis = TypeAdvertisment;
  eViaType = ViaType;

  editForm: FormGroup;
  isEdition = false;
  title = '';
  zipPatternRegex = '^[Kk][Yy]\\d[-\\s]{0,1}\\d{4}$';

  constructor(
    protected advertismentService: AdvertismentService,
    protected addressService: AddressService,
    protected featureService: FeatureService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private currencyPipe: CurrencyPipe,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.editForm = fb.group({
      id: [],
      description: [null, [Validators.required]],
      // createAt: [null, [Validators.required]],
      // modifiedAt: [null, [Validators.required]],
      typeAd: new FormControl(this.eTypeAdvertis.FOR_RENT, Validators.required),
      propertyType: [null, [Validators.required]],
      // active: [null, [Validators.required]],
      price: [this.formatMoney('0'), [Validators.required]],
      // reference: [''],
      address: this.fb.group({
        typeOfVia: [null, [Validators.required]],
        name: [null, [Validators.required]],
        zipCode: [
          null,
          [
            Validators.required,
            //         , Validators.pattern(this.zipPatternRegex)
          ],
        ],
        areaDisctrict: [null, [Validators.required]],
      }),
      feature: this.fb.group({
        numberBedrooms: [null, [Validators.required]],
        numberBathroom: [null, [Validators.required]],
        fullKitchen: [],
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
    this.activatedRoute.data.subscribe(data => {
      this.title = data.pageTitle;
    });
    this.activatedRoute.data.subscribe(({ advertisment }) => {
      if (advertisment) {
        this.isEdition = true;
      }

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
    this.crearListeners();
  }

  updateForm(advertisment: IAdvertisment): void {
    this.editForm.patchValue({
      id: advertisment.id,
      description: advertisment.description,
      // createAt: advertisment.createAt,
      // modifiedAt: advertisment.modifiedAt,
      typeAd: this.isEdition ? advertisment.typeAd : this.eTypeAdvertis.FOR_RENT,
      propertyType: this.isEdition ? advertisment.propertyType : null,
      // active: advertisment.active,
      price: advertisment?.price ? this.formatMoney('' + advertisment.price) : '0',
      // reference: advertisment.reference,
      address: {
        typeOfVia: this.isEdition ? advertisment.address?.typeOfVia : null,
        name: advertisment.address?.name,
        zipCode: advertisment.address?.zipCode,
        areaDisctrict: this.isEdition ? advertisment.address?.areaDisctrict : null,
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
    const ad = {
      ...new Advertisment(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      // createAt: this.editForm.get(['createAt'])!.value,
      // modifiedAt: this.editForm.get(['modifiedAt'])!.value,
      typeAd: this.editForm.get(['typeAd'])!.value,
      propertyType: this.editForm.get(['propertyType'])!.value,
      // active: this.editForm.get(['active'])!.value,
      price: this.hidePrice,
      // reference: this.editForm.get(['reference'])!.value,
      address: {
        typeOfVia: this.editForm.get(['address', 'typeOfVia'])!.value,
        name: this.editForm.get(['address', 'name'])!.value,
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
      // user: this.editForm.get(['user'])!.value,
    };
    console.log(ad);
    return ad;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvertisment>>): void {
    result.subscribe(
      resp => this.onSaveSuccess(resp.body?.id!),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(idNewAd: number): void {
    this.isSaving = false;
    // this.previousState();
    this.router.navigate(['/image/new'], {
      queryParams: {
        idAdv: idNewAd,
      },
    });
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  crearListeners(): void {
    this.editForm.valueChanges.subscribe(valor => {
      console.log(valor);
    });

    // this.forma.statusChanges.subscribe( status => console.log({ status }));
    // this.forma.get('nombre').valueChanges.subscribe( console.log );
  }

  transformTotal(): void {
    const valor = this.editForm.controls.price.value;
    this.editForm.controls.price.setValue(
      /* eslint-disable-next-line */
      this.formatMoney(valor.toString().replace(/\,/g, '')),
      { emitEvent: false }
    );
  }

  formatMoney(value: string): string {
    this.hidePrice = +value;
    /* eslint-disable-next-line */
    const temp = `${value}`.toString().replace(/\,/g, '');
    const converted = this.currencyPipe.transform(temp, 'KYD ')?.replace('$', '') || '';
    /* eslint-disable-next-line */
    console.log(converted);
    return converted;
  }
}
