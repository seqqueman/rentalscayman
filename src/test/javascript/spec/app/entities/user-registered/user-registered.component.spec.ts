import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RentalscaymanTestModule } from '../../../test.module';
import { UserRegisteredComponent } from 'app/entities/user-registered/user-registered.component';
import { UserRegisteredService } from 'app/entities/user-registered/user-registered.service';
import { UserRegistered } from 'app/shared/model/user-registered.model';

describe('Component Tests', () => {
  describe('UserRegistered Management Component', () => {
    let comp: UserRegisteredComponent;
    let fixture: ComponentFixture<UserRegisteredComponent>;
    let service: UserRegisteredService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentalscaymanTestModule],
        declarations: [UserRegisteredComponent],
      })
        .overrideTemplate(UserRegisteredComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserRegisteredComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserRegisteredService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserRegistered(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userRegistereds && comp.userRegistereds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
