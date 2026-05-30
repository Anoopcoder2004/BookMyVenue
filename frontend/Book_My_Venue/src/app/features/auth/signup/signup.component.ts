import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  name: string = '';
  email: string = '';
  password: string = '';
  role: string = 'USER';

  isLoading = false;
  error: string = '';
  roles = [
  { label: 'User', value: 'USER' },
  { label: 'Venue Owner', value: 'OWNER' },
  { label: 'Admin', value: 'ADMIN' }
];

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  onSignup() {
    this.isLoading = true;
    this.error = '';

    this.authService.signup({
      name: this.name,
      email: this.email,
      password: this.password,
      role: this.role
    }).subscribe({
      next: (res) => {
        console.log('Signup success', res);

        // optional: auto-login or redirect
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.error = err?.error?.message || 'Signup failed';
        this.isLoading = false;
      }
    });
  }
}