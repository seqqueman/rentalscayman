import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RentalscaymanSharedModule } from 'app/shared/shared.module';
import { RentalscaymanCoreModule } from 'app/core/core.module';
import { RentalscaymanAppRoutingModule } from './app-routing.module';
import { RentalscaymanHomeModule } from './home/home.module';
import { RentalscaymanEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RentalscaymanSharedModule,
    RentalscaymanCoreModule,
    RentalscaymanHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RentalscaymanEntityModule,
    RentalscaymanAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class RentalscaymanAppModule {}
