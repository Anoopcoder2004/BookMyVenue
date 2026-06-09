import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../../core/services/admin.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit {

  venues: any[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.loadVenues();
  }

  loadVenues() {
    this.adminService.getPendingVenues(0, 10)
      .subscribe((res: any) => {
        this.venues = res.content;
      });
  }

approve(id: number) {
  console.log("🚀 APPROVE CLICKED:", id);

  this.adminService.approveVenue(id).subscribe({
    next: (res) => {
      console.log("✅ APPROVED:", res);

      setTimeout(() => {
        console.log("🔄 REFRESHING LIST");
        this.loadVenues();
      }, 0);
    },
    error: (err) => {
      console.error("❌ ERROR:", err);
    }
  });
}

  reject(id: number) {
    const reason = prompt("Enter rejection reason:");
    if (!reason) return;

    this.adminService.rejectVenue(id, reason).subscribe(() => {
      this.loadVenues();
    });
  }
}