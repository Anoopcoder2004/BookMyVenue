import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-homescreen',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './homescreen.component.html',
  styleUrl: './homescreen.component.scss'
})
export class HomescreenComponent {

  searchText: string = '';
  venues = [
    {
      name: 'Skyline Rooftop',
      location: 'Kochi',
      price: 5000,
      type: 'Rooftop',
      image: 'https://images.unsplash.com/photo-1521334884684-d80222895322'
    },
    {
      name: 'Green Garden Hall',
      location: 'Ernakulam',
      price: 8000,
      type: 'Outdoor',
      image: 'https://images.unsplash.com/photo-1505691938895-1758d7feb511'
    },
    {
      name: 'Cozy Cafe Space',
      location: 'Kakkanad',
      price: 2000,
      type: 'Cafe',
      image: 'https://images.unsplash.com/photo-1554118811-1e0d58224f24'
    },

    // ➕ New venues

    {
      name: 'Grand Lotus Auditorium',
      location: 'Kochi',
      price: 12000,
      type: 'Auditorium',
      image: 'https://images.unsplash.com/photo-1503424886307-b090341d25d1'
    },
    {
      name: 'Sea Breeze Lawn',
      location: 'Fort Kochi',
      price: 7000,
      type: 'Outdoor',
      image: 'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee'
    },
    {
      name: 'Urban Studio Space',
      location: 'Kaloor',
      price: 3500,
      type: 'Studio',
      image: 'https://images.unsplash.com/photo-1521737604893-d14cc237f11d'
    },
    {
      name: 'Royal Banquet Hall',
      location: 'Edappally',
      price: 15000,
      type: 'Banquet',
      image: 'https://images.unsplash.com/photo-1528605248644-14dd04022da1'
    },
    {
      name: 'Creative Hub Workspace',
      location: 'MG Road',
      price: 2500,
      type: 'Co-working',
      image: 'https://images.unsplash.com/photo-1497366216548-37526070297c'
    }
  ];

  onSearch() {
    console.log('Searching for:', this.searchText);
    // later you can route or filter venues here
  }

  clearSearch() {
    this.searchText = '';
  }

}
