import { IImage } from 'app/shared/model/image.model';

export interface ICompany {
  id?: number;
  name?: string;
  phone?: string;
  email?: string;
  logo?: IImage;
}

export class Company implements ICompany {
  constructor(public id?: number, public name?: string, public phone?: string, public email?: string, public logo?: IImage) {}
}
