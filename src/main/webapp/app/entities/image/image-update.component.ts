import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormArray, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IImage, Image } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAdvertisment } from 'app/shared/model/advertisment.model';
import { AdvertismentService } from 'app/entities/advertisment/advertisment.service';

@Component({
  selector: 'jhi-image-update',
  templateUrl: './image-update.component.html',
})
export class ImageUpdateComponent implements OnInit {
  isSaving = false;
  // advertisments: IAdvertisment[] = [];
  createdDp: any;
  // ad: IAdvertisment = {};
  idAdv = 1;
  errorLaunched = false;
  private selectedFile: any;
  private title = '';

  archivos: string[] = [];
  photos: IImage[] = [];

  editForm = this.fb.group({
    imagess: this.fb.array([
      this.fb.group({
        id: [],
        idImage: [],
        img: [null, [Validators.required]],
        imgContentType: [],
        description: [],
      }),
    ]),
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

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe(param => {
      this.idAdv = +param.get('idAdv')!;
    });
    this.activatedRoute.data.subscribe(({ image }) => {
      this.title = image.pageTitle;
      // this.updateForm(image);

      // this.advertismentService.query().subscribe((res: HttpResponse<IAdvertisment[]>) => (this.advertisments = res.body || []));
    });
  }

  updateForm(image: IImage): void {
    this.editForm.patchValue({
      id: this.idAdv,
      idImage: image.id,
      img: image.img,
      imgContentType: image.imgContentType,
      description: image.description,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean, index: number): void {
    const fmgroup = this.imagess.controls[index] as FormGroup;
    this.dataUtils.loadFileToForm(event, fmgroup, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.errorLaunched = true;
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('rentalscaymanApp.error', { message: err.message })
      );
    });
    if (!this.errorLaunched) {
      this.archivos.push(event.target!.files[0].name);
      // this.selectedFile = event.target!.files[0];
    }
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string, index: number): void {
    const fmgroup = this.imagess.controls[index] as FormGroup;
    fmgroup.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    this.imagess.removeAt(index);
    this.archivos.splice(index, 1);
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    // const image = this.createFromForm();
    // if (image.id !== undefined) {
    //   this.subscribeToSaveResponse(this.imageService.update(image));
    // } else {
    //   // this.subscribeToSaveResponse(this.imageService.create(image));
    //   this.subscribeToSaveResponse(this.imageService.uploadImage(this.editForm.get(['id'])!.value, this.selectedFile, image.description!));
    // }
    this.subscribeToSaveResponse(this.imageService.uploadImages(this.idAdv + '', this.createFromFormArray()));
  }

  private createFromFormArray(): IImage[] {
    this.photos = [];
    for (let index = 0; index < this.imagess.length; index++) {
      const photo = {
        ...new Image(),
        id: this.imagess.controls[index].get(['idImage'])!.value,
        img: this.imagess.controls[index].get(['img'])!.value,
        description: this.imagess.controls[index].get(['description'])!.value,
        name: this.archivos[index],
        imgContentType: this.imagess.controls[index].get(['imgContentType'])!.value,
      };
      this.photos.push(photo);
    }

    return this.photos;
  }

  private createFromForm(): IImage {
    return {
      ...new Image(),
      id: this.editForm.get(['idImage'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      description: this.editForm.get(['description'])!.value,
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

  addNewImageToModel(): void {
    this.imagess.push(
      this.fb.group({
        id: [],
        idImage: [],
        img: [null, [Validators.required]],
        imgContentType: [''],
        description: [''],
      })
    );
  }

  get imagess(): FormArray {
    return this.editForm.get('imagess') as FormArray;
  }

  /*
  detectFiles(event: any, index: number): void {
    const files = event.target.files;
    if (files) {
      for (const file of files) {
        const reader = new FileReader();
        reader.onload = (e: any) => {

          // console.log("e.target.result", e.target.result);
          this.imagess.push(

            this.fb.group({
              id: this.idAdv,
              idImage: '',
              img: e.target.result,
              imgContentType: file.type,
              description: this.imagess.controls[index].get('description')!.value,
            })

            //   this.createItem({
            //     file,
            //     url: e.target.result  //Base64 string for preview image
            // })
          );
          // eslint-disable-next-line no-console
          // console.log(JSON.stringify(this.imagess));
        }
        reader.readAsDataURL(file);
      }
    }
  }
  */
}
