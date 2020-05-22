import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RentalscaymanTestModule } from '../../../test.module';
import { UserRegisteredUpdateComponent } from 'app/entities/user-registered/user-registered-update.component';
import { UserRegisteredService } from 'app/entities/user-registered/user-registered.service';
import { UserRegistered } from 'app/shared/model/user-registered.model';

describe('Component Tests', () => {
  describe('UserRegistered Management Update Component', () => {
    let comp: UserRegisteredUpdateComponent;
    let fixture: ComponentFixture<UserRegisteredUpdateComponent>;
    let service: UserRegisteredService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentalscaymanTestModule],
        declarations: [UserRegisteredUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserRegisteredUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserRegisteredUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserRegisteredService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserRegistered(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserRegistered();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
