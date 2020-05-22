import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FeatureService } from 'app/entities/feature/feature.service';
import { IFeature, Feature } from 'app/shared/model/feature.model';

describe('Service Tests', () => {
  describe('Feature Service', () => {
    let injector: TestBed;
    let service: FeatureService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeature;
    let expectedResult: IFeature | IFeature[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FeatureService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Feature(0, 0, 0, false, false, false, false, false, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Feature', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Feature()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Feature', () => {
        const returnedFromService = Object.assign(
          {
            numberBedrooms: 1,
            numberBathroom: 1,
            fullKitchen: true,
            elevator: true,
            parking: true,
            airConditionair: true,
            backyard: true,
            pool: true,
            m2: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Feature', () => {
        const returnedFromService = Object.assign(
          {
            numberBedrooms: 1,
            numberBathroom: 1,
            fullKitchen: true,
            elevator: true,
            parking: true,
            airConditionair: true,
            backyard: true,
            pool: true,
            m2: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Feature', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
