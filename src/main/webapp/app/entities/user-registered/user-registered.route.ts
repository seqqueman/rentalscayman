import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserRegistered, UserRegistered } from 'app/shared/model/user-registered.model';
import { UserRegisteredService } from './user-registered.service';
import { UserRegisteredComponent } from './user-registered.component';
import { UserRegisteredDetailComponent } from './user-registered-detail.component';
import { UserRegisteredUpdateComponent } from './user-registered-update.component';

@Injectable({ providedIn: 'root' })
export class UserRegisteredResolve implements Resolve<IUserRegistered> {
  constructor(private service: UserRegisteredService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserRegistered> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userRegistered: HttpResponse<UserRegistered>) => {
          if (userRegistered.body) {
            return of(userRegistered.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserRegistered());
  }
}

export const userRegisteredRoute: Routes = [
  {
    path: '',
    component: UserRegisteredComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserRegistereds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserRegisteredDetailComponent,
    resolve: {
      userRegistered: UserRegisteredResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserRegistereds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserRegisteredUpdateComponent,
    resolve: {
      userRegistered: UserRegisteredResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserRegistereds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserRegisteredUpdateComponent,
    resolve: {
      userRegistered: UserRegisteredResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserRegistereds',
    },
    canActivate: [UserRouteAccessService],
  },
];
