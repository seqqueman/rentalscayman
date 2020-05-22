import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentalscaymanSharedModule } from 'app/shared/shared.module';
import { UserRegisteredComponent } from './user-registered.component';
import { UserRegisteredDetailComponent } from './user-registered-detail.component';
import { UserRegisteredUpdateComponent } from './user-registered-update.component';
import { UserRegisteredDeleteDialogComponent } from './user-registered-delete-dialog.component';
import { userRegisteredRoute } from './user-registered.route';

@NgModule({
  imports: [RentalscaymanSharedModule, RouterModule.forChild(userRegisteredRoute)],
  declarations: [
    UserRegisteredComponent,
    UserRegisteredDetailComponent,
    UserRegisteredUpdateComponent,
    UserRegisteredDeleteDialogComponent,
  ],
  entryComponents: [UserRegisteredDeleteDialogComponent],
})
export class RentalscaymanUserRegisteredModule {}
