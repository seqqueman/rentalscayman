import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'noimage',
})
export class NoimagePipe implements PipeTransform {
  transform(images: any[]): string {
    if (!images?.length) {
      return 'content/images/noimage.png';
    }
    if (images.length > 0) {
      return images[0].url;
    }
    return '';
  }
}
