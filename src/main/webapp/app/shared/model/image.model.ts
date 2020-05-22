import { Moment } from 'moment';
import { IAdvertisment } from 'app/shared/model/advertisment.model';

export interface IImage {
  id?: number;
  name?: string;
  created?: Moment;
  imgContentType?: string;
  img?: any;
  description?: string;
  url?: string;
  advertisment?: IAdvertisment;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public name?: string,
    public created?: Moment,
    public imgContentType?: string,
    public img?: any,
    public description?: string,
    public url?: string,
    public advertisment?: IAdvertisment
  ) {}
}
