import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';
import { IFeature } from 'app/shared/model/feature.model';
import { IImage } from 'app/shared/model/image.model';
import { IUser } from 'app/core/user/user.model';
import { TypeAdvertisment } from 'app/shared/model/enumerations/type-advertisment.model';
import { PropertyType } from 'app/shared/model/enumerations/property-type.model';

export interface IAdvertisment {
  id?: number;
  description?: string;
  createAt?: Moment;
  modifiedAt?: Moment;
  typeAd?: TypeAdvertisment;
  propertyType?: PropertyType;
  active?: boolean;
  price?: number;
  reference?: string;
  address?: IAddress;
  feature?: IFeature;
  images?: IImage[];
  user?: IUser;
}

export class Advertisment implements IAdvertisment {
  constructor(
    public id?: number,
    public description?: string,
    public createAt?: Moment,
    public modifiedAt?: Moment,
    public typeAd?: TypeAdvertisment,
    public propertyType?: PropertyType,
    public active?: boolean,
    public price?: number,
    public reference?: string,
    public address?: IAddress,
    public feature?: IFeature,
    public images?: IImage[],
    public user?: IUser
  ) {
    this.active = this.active || false;
  }
}
