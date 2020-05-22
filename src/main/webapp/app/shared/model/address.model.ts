import { ViaType } from 'app/shared/model/enumerations/via-type.model';
import { AreaDisctrict } from 'app/shared/model/enumerations/area-disctrict.model';

export interface IAddress {
  id?: number;
  typeOfVia?: ViaType;
  number?: string;
  zipCode?: string;
  areaDisctrict?: AreaDisctrict;
  lat?: number;
  lon?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public typeOfVia?: ViaType,
    public number?: string,
    public zipCode?: string,
    public areaDisctrict?: AreaDisctrict,
    public lat?: number,
    public lon?: number
  ) {}
}
