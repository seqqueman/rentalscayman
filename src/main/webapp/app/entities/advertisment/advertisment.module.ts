import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentalscaymanSharedModule } from 'app/shared/shared.module';
import { AdvertismentComponent } from './advertisment.component';
import { AdvertismentDetailComponent } from './advertisment-detail.component';
import { AdvertismentUpdateComponent } from './advertisment-update.component';
import { AdvertismentDeleteDialogComponent } from './advertisment-delete-dialog.component';
import { advertismentRoute } from './advertisment.route';

@NgModule({
  imports: [RentalscaymanSharedModule, RouterModule.forChild(advertismentRoute)],
  declarations: [AdvertismentComponent, AdvertismentDetailComponent, AdvertismentUpdateComponent, AdvertismentDeleteDialogComponent],
  entryComponents: [AdvertismentDeleteDialogComponent],
})
export class RentalscaymanAdvertismentModule {}
