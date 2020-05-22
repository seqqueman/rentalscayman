import { IUser } from 'app/core/user/user.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IUserRegistered {
  id?: number;
  advertiser?: boolean;
  phone?: string;
  numberOfAds?: number;
  user?: IUser;
  company?: ICompany;
}

export class UserRegistered implements IUserRegistered {
  constructor(
    public id?: number,
    public advertiser?: boolean,
    public phone?: string,
    public numberOfAds?: number,
    public user?: IUser,
    public company?: ICompany
  ) {
    this.advertiser = this.advertiser || false;
  }
}
