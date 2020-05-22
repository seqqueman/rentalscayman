import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentalscaymanTestModule } from '../../../test.module';
import { FeatureDetailComponent } from 'app/entities/feature/feature-detail.component';
import { Feature } from 'app/shared/model/feature.model';

describe('Component Tests', () => {
  describe('Feature Management Detail Component', () => {
    let comp: FeatureDetailComponent;
    let fixture: ComponentFixture<FeatureDetailComponent>;
    const route = ({ data: of({ feature: new Feature(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentalscaymanTestModule],
        declarations: [FeatureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FeatureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeatureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feature on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feature).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
