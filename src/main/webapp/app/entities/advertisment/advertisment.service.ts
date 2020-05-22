import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdvertisment } from 'app/shared/model/advertisment.model';

type EntityResponseType = HttpResponse<IAdvertisment>;
type EntityArrayResponseType = HttpResponse<IAdvertisment[]>;

@Injectable({ providedIn: 'root' })
export class AdvertismentService {
  public resourceUrl = SERVER_API_URL + 'api/advertisments';

  constructor(protected http: HttpClient) {}

  create(advertisment: IAdvertisment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(advertisment);
    return this.http
      .post<IAdvertisment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(advertisment: IAdvertisment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(advertisment);
    return this.http
      .put<IAdvertisment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAdvertisment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdvertisment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(advertisment: IAdvertisment): IAdvertisment {
    const copy: IAdvertisment = Object.assign({}, advertisment, {
      createAt: advertisment.createAt && advertisment.createAt.isValid() ? advertisment.createAt.format(DATE_FORMAT) : undefined,
      modifiedAt: advertisment.modifiedAt && advertisment.modifiedAt.isValid() ? advertisment.modifiedAt.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createAt = res.body.createAt ? moment(res.body.createAt) : undefined;
      res.body.modifiedAt = res.body.modifiedAt ? moment(res.body.modifiedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((advertisment: IAdvertisment) => {
        advertisment.createAt = advertisment.createAt ? moment(advertisment.createAt) : undefined;
        advertisment.modifiedAt = advertisment.modifiedAt ? moment(advertisment.modifiedAt) : undefined;
      });
    }
    return res;
  }
}
