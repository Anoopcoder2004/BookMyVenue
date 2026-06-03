import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';
import { VenueService } from '../../../core/services/venue.service';
@Component({
  selector: 'app-add-venue',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-venue.component.html',
  styleUrl: './add-venue.component.scss'
})
export class AddVenueComponent implements OnInit {

  venueForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private venueService: VenueService
  ) {}

  ngOnInit(): void {
    this.venueForm = this.fb.group({
      name: ['', Validators.required],
      location: ['', Validators.required],
      price: [null, Validators.required],
      capacity: [null, Validators.required],
      description: ['']
    });
  }

  onSubmit() {
    if (this.venueForm.invalid) return;

    this.venueService.createVenue(this.venueForm.value).subscribe({
      next: (res) => {
        console.log('Venue created:', res);
        this.venueForm.reset();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}