import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.RentalscaymanAddressModule),
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.RentalscaymanCompanyModule),
      },
      {
        path: 'image',
        loadChildren: () => import('./image/image.module').then(m => m.RentalscaymanImageModule),
      },
      {
        path: 'user-registered',
        loadChildren: () => import('./user-registered/user-registered.module').then(m => m.RentalscaymanUserRegisteredModule),
      },
      {
        path: 'feature',
        loadChildren: () => import('./feature/feature.module').then(m => m.RentalscaymanFeatureModule),
      },
      {
        path: 'advertisment',
        loadChildren: () => import('./advertisment/advertisment.module').then(m => m.RentalscaymanAdvertismentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RentalscaymanEntityModule {}
