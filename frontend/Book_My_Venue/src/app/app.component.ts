import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToastComponent } from './shared/components/toast/toast.component';
import { Toast } from './shared/components/toast/toast.model';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,ToastComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Book_My_Venue';
  toast: Toast = {
  title: 'Success',
  message: 'This is a test toast.',
  type: 'success',
  duration: 4000
};

closeToast() {
  console.log('Closed');
}
}
