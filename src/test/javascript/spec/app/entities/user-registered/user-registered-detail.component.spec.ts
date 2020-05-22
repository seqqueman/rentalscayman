import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentalscaymanTestModule } from '../../../test.module';
import { UserRegisteredDetailComponent } from 'app/entities/user-registered/user-registered-detail.component';
import { UserRegistered } from 'app/shared/model/user-registered.model';

describe('Component Tests', () => {
  describe('UserRegistered Management Detail Component', () => {
    let comp: UserRegisteredDetailComponent;
    let fixture: ComponentFixture<UserRegisteredDetailComponent>;
    const route = ({ data: of({ userRegistered: new UserRegistered(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentalscaymanTestModule],
        declarations: [UserRegisteredDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserRegisteredDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserRegisteredDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userRegistered on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userRegistered).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
