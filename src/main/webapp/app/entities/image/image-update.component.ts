import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IImage, Image } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAdvertisment, Advertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from 'app/entities/advertisment/advertisment.service';

@Component({
  selector: 'jhi-image-update',
  templateUrl: './image-update.component.html',
})
export class ImageUpdateComponent implements OnInit {
  isSaving = false;
  advertisments: IAdvertisment[] = [];
  createdDp: any;
  ad: IAdvertisment = {};
  idAdv = 1;
  errorLaunched = false;
  private selectedFile: any;

  editForm = this.fb.group({
    id: [],
    idImage: [],
    // name: [],
    // created: [],
    img: [null, [Validators.required]],
    imgContentType: [],
    description: [],
    // url: [],
    // advertisment: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected imageService: ImageService,
    protected advertismentService: AdvertismentService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  // ngOnInit(): void {
  //   this.activatedRoute.data.subscribe(({ image }) => {
  //     this.updateForm(image);

  //     this.advertismentService.query().subscribe((res: HttpResponse<IAdvertisment[]>) => (this.advertisments = res.body || []));
  //   });
  // }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ image }) => {
      this.updateForm(image);
      this.activatedRoute.paramMap.subscribe(param => {
        // const idAdvertisment: number = +param.get('id')!;
        // if (idAdvertisment) {
        //   this.advertismentService.find(idAdvertisment).subscribe((res: HttpResponse<IAdvertisment>) => {
        //     this.ad = res.body || new Advertisment();
        //   });
        // }
        this.idAdv = +param.get('id')!;
      });
      // this.advertismentService.query().subscribe((res: HttpResponse<IAdvertisment[]>) => (this.advertisments = res.body || []));
    });
  }

  updateForm(image: IImage): void {
    this.editForm.patchValue({
      id: this.idAdv,
      idImage: image.id,
      // name: image.name,
      // created: image.created,
      img: image.img,
      imgContentType: image.imgContentType,
      description: image.description,
      // url: image.url,
      // advertisment: image.advertisment,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.errorLaunched = true;
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('rentalscaymanApp.error', { message: err.message })
      );
    });
    if (!this.errorLaunched) {
      this.selectedFile = event.target!.files[0];
    }
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const image = this.createFromForm();
    if (image.id !== undefined) {
      this.subscribeToSaveResponse(this.imageService.update(image));
    } else {
      // this.subscribeToSaveResponse(this.imageService.create(image));
      this.subscribeToSaveResponse(this.imageService.uploadImage(this.editForm.get(['id'])!.value, this.selectedFile, image.description!));
    }
  }

  private createFromForm(): IImage {
    return {
      ...new Image(),
      id: this.editForm.get(['idImage'])!.value,
      // name: this.editForm.get(['name'])!.value,
      // created: this.editForm.get(['created'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      description: this.editForm.get(['description'])!.value,
      // url: this.editForm.get(['url'])!.value,
      // advertisment: this.editForm.get(['advertisment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAdvertisment): any {
    return item.id;
  }
}
