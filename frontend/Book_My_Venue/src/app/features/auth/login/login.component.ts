import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  isLoading = false;
  error: string = '';
    showPassword = false;



  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  onLogin() {
    this.isLoading = true;

    this.authService.login({
      email: this.email,
      password: this.password
    }).subscribe({
      next: (res) => {
        const { token, role } = res.data;

        localStorage.setItem('token', token);
        localStorage.setItem('role', role);

        this.isLoading = false; // ✅ add this

        this.redirectByRole(role);
      },
      error: () => {
        this.error = 'Invalid credentials';
        this.isLoading = false;
      }
    });
  }
  goToSignup() {
    this.router.navigate(['/signup']);
  }
  redirectByRole(role: string) {

    switch (role) {
      case 'ADMIN':
        this.router.navigate(['/admin-dashboard']);
        break;

      case 'OWNER':
        this.router.navigate(['/owner-dashboard']);
        break;

      case 'USER':
      default:
        this.router.navigate(['/venue-details']);
        break;
    }
  }

togglePassword() {
  this.showPassword = !this.showPassword;
}
}