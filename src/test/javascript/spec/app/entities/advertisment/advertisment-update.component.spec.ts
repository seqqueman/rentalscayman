import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RentalscaymanTestModule } from '../../../test.module';
import { AdvertismentUpdateComponent } from 'app/entities/advertisment/advertisment-update.component';
import { AdvertismentService } from 'app/entities/advertisment/advertisment.service';
import { Advertisment } from 'app/shared/model/advertisment.model';

describe('Component Tests', () => {
  describe('Advertisment Management Update Component', () => {
    let comp: AdvertismentUpdateComponent;
    let fixture: ComponentFixture<AdvertismentUpdateComponent>;
    let service: AdvertismentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentalscaymanTestModule],
        declarations: [AdvertismentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdvertismentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvertismentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvertismentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Advertisment(123);
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
        const entity = new Advertisment();
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
