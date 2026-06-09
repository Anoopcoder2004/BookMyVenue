import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AdminService {

  private baseUrl = 'http://localhost:8080/admin/venues';

  constructor(private http: HttpClient) {}

  getPendingVenues(page: number, size: number) {
    return this.http.get<any>(
      `${this.baseUrl}/pending?page=${page}&size=${size}`
    );
  }

  approveVenue(id: number) {
    return this.http.put(`${this.baseUrl}/${id}/approve`, {});
  }

  rejectVenue(id: number, reason: string) {
    return this.http.put(`${this.baseUrl}/${id}/reject`, { reason });
  }
}