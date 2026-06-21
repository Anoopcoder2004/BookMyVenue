import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VenueService {

  private baseUrl = 'http://localhost:8080/api/venues';

  constructor(private http: HttpClient) {}

  // 🔹 1. Get all venues
  getVenues(page: number = 0, size: number = 1000, city?: string): Observable<any> {
    let url = `${this.baseUrl}?page=${page}&size=${size}`;

    if (city) {
      url += `&city=${city}`;
    }

    return this.http.get(url);
  }

  // 🔹 2. Get venue by ID
  getVenueById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // 🔹 3. Create venue (OWNER)
  createVenue(venue: any): Observable<any> {
    return this.http.post(this.baseUrl, venue);
  }
  // for owners to see their venues 
  // 🔥 Get logged-in owner's venues (paginated)
  getMyVenues(page: number = 0, size: number = 5): Observable<any> {
    return this.http.get<any>(
      `${this.baseUrl}/my-venues?page=${page}&size=${size}`
    );
  }}