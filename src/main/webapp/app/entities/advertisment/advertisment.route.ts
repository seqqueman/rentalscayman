import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdvertisment, Advertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from './advertisment.service';
import { AdvertismentComponent } from './advertisment.component';
import { AdvertismentDetailComponent } from './advertisment-detail.component';
import { AdvertismentUpdateComponent } from './advertisment-update.component';

@Injectable({ providedIn: 'root' })
export class AdvertismentResolve implements Resolve<IAdvertisment> {
  constructor(private service: AdvertismentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvertisment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((advertisment: HttpResponse<Advertisment>) => {
          if (advertisment.body) {
            return of(advertisment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Advertisment());
  }
}

export const advertismentRoute: Routes = [
  {
    path: '',
    component: AdvertismentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Advertisments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdvertismentDetailComponent,
    resolve: {
      advertisment: AdvertismentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Advertisments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdvertismentUpdateComponent,
    resolve: {
      advertisment: AdvertismentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Advertisments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdvertismentUpdateComponent,
    resolve: {
      advertisment: AdvertismentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Advertisments',
    },
    canActivate: [UserRouteAccessService],
  },
];
