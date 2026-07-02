import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from '../../../shared/components/toast/toast.service'; 
import { AuthService } from '../service/auth.service';
import { finalize } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
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
    isDarkMode = true;





  constructor(
    private router: Router,
    private authService: AuthService,
    private toast : ToastService
  ) { }

  ngOnInit() {
  const savedTheme = localStorage.getItem('theme') ?? 'dark';

  this.isDarkMode = savedTheme === 'dark';

  document.documentElement.setAttribute(
    'data-theme',
    savedTheme
  );
}
toggleTheme() {

  this.isDarkMode = !this.isDarkMode;

  const theme = this.isDarkMode ? 'dark' : 'light';

  document.documentElement.setAttribute(
    'data-theme',
    theme
  );

  localStorage.setItem('theme', theme);
}

onLogin(): void {

  if (!this.isLoginFormValid()) {
    return;
  }

  this.isLoading = true;

  this.login();

}

private isLoginFormValid(): boolean {

  if (!this.email || !this.password) {

    this.toast.warning(
      'Missing Information',
      'Please enter your email and password.'
    );

    return false;
  }

  return true;

}

private login(): void {

  this.authService.login(this.getLoginRequest())
    .pipe(
      finalize(() => this.isLoading = false)
    )
    .subscribe({
      next: (response) => this.handleLoginSuccess(response),
      error: (error) => this.handleLoginError(error)
    });

}

private getLoginRequest() {

  return {
    email: this.email,
    password: this.password
  };

}

private handleLoginSuccess(response: any): void {

  const { token, role } = response.data;

  this.storeUserSession(token, role);

  this.toast.success(
    'Login Successful',
    'Welcome back!'
  );

  this.redirectByRole(role);

}

private handleLoginError(error: HttpErrorResponse): void {

  this.toast.error(
    'Login Failed',
    error.error?.message ?? 'Something went wrong. Please try again.'
  );

}

private storeUserSession(token: string, role: string): void {

  localStorage.setItem('token', token);

  localStorage.setItem('role', role);

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