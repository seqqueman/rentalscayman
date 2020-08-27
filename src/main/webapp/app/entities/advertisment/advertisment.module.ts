import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentalscaymanSharedModule } from 'app/shared/shared.module';
// import { AdvertismentComponent } from './advertisment.component';
import { AdvertismentDetailComponent } from './advertisment-detail.component';
import { AdvertismentUpdateComponent } from './advertisment-update.component';
import { AdvertismentDeleteDialogComponent } from './advertisment-delete-dialog.component';
import { advertismentRoute } from './advertisment.route';
import { ImageUpdateComponent } from '../image/image-update.component';
// import { CardAdComponent } from './card-ad.component';
import { CurrencyPipe } from '@angular/common';

@NgModule({
  imports: [RentalscaymanSharedModule, RouterModule.forChild(advertismentRoute)],
  declarations: [
    // AdvertismentComponent,
    AdvertismentDetailComponent,
    AdvertismentUpdateComponent,
    AdvertismentDeleteDialogComponent,
    ImageUpdateComponent,
    // CardAdComponent,
  ],
  providers: [CurrencyPipe],
  // exports:[AdvertismentComponent],

  entryComponents: [AdvertismentDeleteDialogComponent],
})
export class RentalscaymanAdvertismentModule {}
