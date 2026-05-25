import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {
  email: string = '';
  password: string = '';
  isLoading = false;
  

  constructor(private router: Router) {}

  login() {
    if (!this.email || !this.password) {
      alert('Please enter email and password');
      return;
    }

    this.isLoading = true;

    // dummy delay to simulate API call
    setTimeout(() => {
      localStorage.setItem('user', this.email);
      this.isLoading = false;

      this.router.navigate(['/home']);
    }, 1000);
  }
}