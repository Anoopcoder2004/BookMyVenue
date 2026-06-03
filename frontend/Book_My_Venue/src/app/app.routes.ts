import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';


import { LoginComponent } from './features/auth/login/login.component';
import { HomescreenComponent } from './modules/homescreen/homescreen.component';
import { SignupComponent } from './features/auth/signup/signup.component';
import { VenueDetailsComponent } from './features/user/venue-details/venue-details.component';
import { AdminDashboardComponent } from './features/admin/dashboard/dashboard.component';
import { DashboardComponent } from './features/owner/dashboard/dashboard.component';
import { Component } from '@angular/core';
import { AddVenueComponent } from './features/owner/add-venue/add-venue.component';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomescreenComponent,
    //added authguards to prevent unauthorized access
    //later chagne to Parent Route Guard (apply once)
    canActivate: [authGuard]

  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'owner-dashboard',
    component: DashboardComponent
  },
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent
  },
  {
    path: 'venue-details',
    component: VenueDetailsComponent
  },
  {
    path: 'owner-dashboard/add-venue',
    component: AddVenueComponent
  },


  {
    path: '**',
    redirectTo: 'login'
  }
];