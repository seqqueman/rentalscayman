export interface IFeature {
  id?: number;
  numberBedrooms?: number;
  numberBathroom?: number;
  fullKitchen?: boolean;
  elevator?: boolean;
  parking?: boolean;
  airConditionair?: boolean;
  backyard?: boolean;
  pool?: boolean;
  m2?: number;
}

export class Feature implements IFeature {
  constructor(
    public id?: number,
    public numberBedrooms?: number,
    public numberBathroom?: number,
    public fullKitchen?: boolean,
    public elevator?: boolean,
    public parking?: boolean,
    public airConditionair?: boolean,
    public backyard?: boolean,
    public pool?: boolean,
    public m2?: number
  ) {
    this.fullKitchen = this.fullKitchen || false;
    this.elevator = this.elevator || false;
    this.parking = this.parking || false;
    this.airConditionair = this.airConditionair || false;
    this.backyard = this.backyard || false;
    this.pool = this.pool || false;
  }
}
