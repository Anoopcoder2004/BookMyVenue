import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VenueService } from '../../../core/services/venue.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit{
    venues: any[] = [];

  // 🔥 pagination state
  page = 0;
  size = 5;
  totalPages = 0;


  constructor(
    private router: Router,
    private venueService: VenueService
  ) {}

    ngOnInit(): void {
    this.loadVenues();
  } 
   loadVenues(): void {
    this.venueService.getMyVenues(this.page, this.size)
      .subscribe({
        next: (res: any) => {

          // API response structure:
          // res.content = venues list
          // res.totalPages = pagination info

          this.venues = res.content;
          this.totalPages = res.totalPages;

          console.log('Venues loaded:', res);
        },
        error: (err) => {
          console.error('Error loading venues:', err);
        }
      });
  }

  // 🔥 go to next page
  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadVenues();
    }
  } 
  goToAddVenue() {
    this.router.navigate(['/owner-dashboard/add-venue']);
  }
}