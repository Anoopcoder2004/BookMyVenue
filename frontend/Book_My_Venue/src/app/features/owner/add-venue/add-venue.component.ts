import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { VenueService } from '../../../core/services/venue.service';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-add-venue',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './add-venue.component.html',
  styleUrl: './add-venue.component.scss'
})
export class AddVenueComponent implements OnInit {

  venueForm!: FormGroup;
  selectedFiles: File[] = [];
  previews: string[] = [];
  imageUrls: string[] = [];
  imageError: string = '';

  constructor(
    private fb: FormBuilder,
    private venueService: VenueService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.venueForm = this.fb.group({
      name: ['', Validators.required],
      location: ['', Validators.required],
      price: [null, Validators.required],
      capacity: [null, Validators.required],
      description: ['']
    });
  }

  // onSubmit() {
  //   if (this.venueForm.invalid) return;

  //   this.venueService.createVenue(this.venueForm.value).subscribe({
  //     next: (res) => {
  //       console.log('Venue created:', res);
  //       this.venueForm.reset();
  //     },
  //     error: (err) => {
  //       console.error(err);
  //     }
  //   });
  // }
  onSubmit() {
    if (!this.isFormValid()) return;

    this.uploadImages()
      .subscribe({
        next: (urls) => this.createVenue(urls),
        error: (err) => console.error(err)
      });
  }
  private isFormValid(): boolean {
    if (this.venueForm.invalid) return false;

    if (this.selectedFiles.length === 0) {
      this.imageError = 'At least 1 image is required';
      return false;
    }

    this.imageError = '';
    return true;
  }
  private uploadImages() {
    const formData = new FormData();

    this.selectedFiles.forEach(file => {
      formData.append('files', file);
    });

    return this.http.post<string[]>('http://localhost:8080/files/upload', formData);

  }
  private createVenue(imageUrls: string[]) {
    const payload = {
      ...this.venueForm.value,
      imageUrls
    };

    this.venueService.createVenue(payload).subscribe({
      next: (res) => {
        console.log('Venue created:', res);
        this.resetForm();
      },
      error: (err) => console.error(err)
    });
  }
  private resetForm() {
    this.venueForm.reset();
    this.selectedFiles = [];
    this.previews = [];
    this.imageUrls = [];
  }
  onFileSelect(event: any) {
    const files: FileList = event.target.files;

    this.imageError = '';

    if (files.length + this.selectedFiles.length > 5) {
      this.imageError = 'Maximum 5 images allowed';
      return;
    }

    for (let file of Array.from(files)) {
      this.selectedFiles.push(file);

      const preview = URL.createObjectURL(file);
      this.previews.push(preview);
    }
  }
  removeImage(index: number) {
    this.selectedFiles.splice(index, 1);
    this.previews.splice(index, 1);

  }

}