import { Routes } from '@angular/router';
import { LoginPageComponent } from './modules/login-page/login-page.component';

import { HomescreenComponent } from './modules/homescreen/homescreen.component';
export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'home',
    component: HomescreenComponent
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];