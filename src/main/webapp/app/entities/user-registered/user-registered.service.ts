import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserRegistered } from 'app/shared/model/user-registered.model';

type EntityResponseType = HttpResponse<IUserRegistered>;
type EntityArrayResponseType = HttpResponse<IUserRegistered[]>;

@Injectable({ providedIn: 'root' })
export class UserRegisteredService {
  public resourceUrl = SERVER_API_URL + 'api/user-registereds';

  constructor(protected http: HttpClient) {}

  create(userRegistered: IUserRegistered): Observable<EntityResponseType> {
    return this.http.post<IUserRegistered>(this.resourceUrl, userRegistered, { observe: 'response' });
  }

  update(userRegistered: IUserRegistered): Observable<EntityResponseType> {
    return this.http.put<IUserRegistered>(this.resourceUrl, userRegistered, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserRegistered>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserRegistered[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
