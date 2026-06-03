import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VenueService } from '../../../core/services/venue.service';
import { OnInit } from '@angular/core';

  interface Venue {
  id: number;
  name: string;
  description: string;
  city: string | null;
  address: string | null;
  pricePerDay: number | null;
  capacity: number;
  status: string;
  createdAt: string;
  owner: any | null;
  images: any[];
}


@Component({
  selector: 'app-venue-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './venue-details.component.html',
  styleUrl: './venue-details.component.scss'
})
export class VenueDetailsComponent implements OnInit {

  constructor(
    private venueService: VenueService
  ) { }


venues: Venue[] = [];

  
  ngOnInit(): void {
      this.loadVenues();
  }
  

  bookVenue(venue: any) {
    console.log('Booking venue:', venue);

    // later you will:
    // 1. navigate to booking page
    // 2. pass venueId
  }
  loadVenues() {
    this.venueService.getVenues().subscribe(res => {
      console.log(res);
      this.venues= res.content;
    });

  }
}
