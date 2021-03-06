import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImage } from 'app/shared/model/image.model';

type EntityResponseType = HttpResponse<IImage>;
type EntityArrayResponseType = HttpResponse<IImage[]>;

@Injectable({ providedIn: 'root' })
export class ImageService {
  public resourceUrl = SERVER_API_URL + 'api/images';

  public resourceUploadUrl = SERVER_API_URL + 'api/images/upload';
  resourceUploadUrlMulti = SERVER_API_URL + 'api/images/upload/multi';

  constructor(protected http: HttpClient) {}

  create(image: IImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .post<IImage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  uploadImage(id: string, fileImage: File, description: string): Observable<EntityResponseType> {
    const formData = new FormData();
    formData.append('fileImage', fileImage);
    formData.append('id', id);
    formData.append('description', description);

    return this.http
      .post<IImage>(this.resourceUploadUrl, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  uploadImages(id: string, fileImages: IImage[]): Observable<EntityResponseType> {
    const formData = new FormData();
    formData.append('fileImages', JSON.stringify(fileImages));
    formData.append('id', id);

    return this.http
      .post<IImage>(this.resourceUploadUrlMulti, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(image: IImage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(image);
    return this.http
      .put<IImage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(image: IImage): IImage {
    const copy: IImage = Object.assign({}, image, {
      created: image.created && image.created.isValid() ? image.created.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((image: IImage) => {
        image.created = image.created ? moment(image.created) : undefined;
      });
    }
    return res;
  }
}
