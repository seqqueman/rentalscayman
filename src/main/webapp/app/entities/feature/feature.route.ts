import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFeature, Feature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';
import { FeatureComponent } from './feature.component';
import { FeatureDetailComponent } from './feature-detail.component';
import { FeatureUpdateComponent } from './feature-update.component';

@Injectable({ providedIn: 'root' })
export class FeatureResolve implements Resolve<IFeature> {
  constructor(private service: FeatureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeature> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((feature: HttpResponse<Feature>) => {
          if (feature.body) {
            return of(feature.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Feature());
  }
}

export const featureRoute: Routes = [
  {
    path: '',
    component: FeatureComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Features',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeatureDetailComponent,
    resolve: {
      feature: FeatureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Features',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeatureUpdateComponent,
    resolve: {
      feature: FeatureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Features',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeatureUpdateComponent,
    resolve: {
      feature: FeatureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Features',
    },
    canActivate: [UserRouteAccessService],
  },
];
