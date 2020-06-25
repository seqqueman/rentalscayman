import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RentalscaymanSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
// import { RentalscaymanAdvertismentModule } from 'app/entities/advertisment/advertisment.module';
import { AdvertismentComponent } from 'app/entities/advertisment/advertisment.component';
import { CardAdComponent } from 'app/entities/advertisment/card-ad.component';
import { SearchComponent } from './search.component';

@NgModule({
  imports: [
    RentalscaymanSharedModule,
    // RentalscaymanAdvertismentModule,
    RouterModule.forChild([HOME_ROUTE]),
  ],
  declarations: [HomeComponent, AdvertismentComponent, CardAdComponent, SearchComponent],
})
export class RentalscaymanHomeModule {}
