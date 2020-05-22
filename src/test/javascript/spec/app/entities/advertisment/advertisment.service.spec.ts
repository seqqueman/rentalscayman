import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AdvertismentService } from 'app/entities/advertisment/advertisment.service';
import { IAdvertisment, Advertisment } from 'app/shared/model/advertisment.model';
import { TypeAdvertisment } from 'app/shared/model/enumerations/type-advertisment.model';
import { PropertyType } from 'app/shared/model/enumerations/property-type.model';

describe('Service Tests', () => {
  describe('Advertisment Service', () => {
    let injector: TestBed;
    let service: AdvertismentService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdvertisment;
    let expectedResult: IAdvertisment | IAdvertisment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdvertismentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Advertisment(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        TypeAdvertisment.FOR_RENT,
        PropertyType.NEWDEVELOPMENT,
        false,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createAt: currentDate.format(DATE_FORMAT),
            modifiedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Advertisment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createAt: currentDate.format(DATE_FORMAT),
            modifiedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Advertisment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Advertisment', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            createAt: currentDate.format(DATE_FORMAT),
            modifiedAt: currentDate.format(DATE_FORMAT),
            typeAd: 'BBBBBB',
            propertyType: 'BBBBBB',
            active: true,
            price: 1,
            reference: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Advertisment', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            createAt: currentDate.format(DATE_FORMAT),
            modifiedAt: currentDate.format(DATE_FORMAT),
            typeAd: 'BBBBBB',
            propertyType: 'BBBBBB',
            active: true,
            price: 1,
            reference: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Advertisment', () => {
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
