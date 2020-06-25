/* eslint-disable no-console */
/* eslint-disable */
import { Component, OnInit } from '@angular/core';
import { TypeAdvertisment } from 'app/shared/model/enumerations/type-advertisment.model';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { PropertyType } from 'app/shared/model/enumerations/property-type.model';
import { Advertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from 'app/entities/advertisment/advertisment.service';
import { Router } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'jhi-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  eTypeAdvertis = TypeAdvertisment;
  ePropertyType = PropertyType;

  formSearch: FormGroup;
  headers = new HttpHeaders();

  constructor(private fb: FormBuilder, private advertismentService: AdvertismentService, private router: Router) {
    this.formSearch = this.fb.group({
      typeAd: new FormControl(Object.keys(TypeAdvertisment)[0]),
      typeBuilding: [null],
      area: [],
    });
  }

  ngOnInit(): void {}

  // tslint:disable-next-line: typedef
  searchAds() {
    let addcoma = false;
    let querySearch = '';
    if (this.formSearch.get('typeAd')?.value) {
      addcoma = true;
      querySearch += 'typeAd:' + this.formSearch.get('typeAd')?.value;
    }
    if (this.formSearch.get('typeBuilding')?.value) {
      if (addcoma) querySearch = querySearch + ',';
      querySearch += 'propertyType:' + this.formSearch.get('typeBuilding')?.value;
      addcoma = true;
    }
    if (this.formSearch.get('area')?.value) {
      if (addcoma) querySearch = querySearch + ',';
      querySearch += 'areaDisctrict:' + this.formSearch.get('area')?.value + ',';
    }

    this.router.navigate(['/advertisment'], {
      queryParams: {
        page: 1,
        size: Number(this.headers.get('X-Total-Count')),
        sort: 'createAt' + ',' + 'desc',
        search: querySearch,
      },
    });
    // this.advertisments = data || [];
  }
}
