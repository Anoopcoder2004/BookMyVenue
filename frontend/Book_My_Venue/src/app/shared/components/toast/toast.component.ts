import {
  Component,
  OnDestroy,
  OnInit
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

import { Toast } from './toast.model';
import { ToastService } from './toast.service';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.scss'
})
export class ToastComponent implements OnInit, OnDestroy {

  toast: Toast | null = null;

  visible = false;

  progress = 100;

  private subscription?: Subscription;

  private intervalId?: ReturnType<typeof setInterval>;

  private timeoutId?: ReturnType<typeof setTimeout>;

  constructor(
    private toastService: ToastService
  ) {}

  ngOnInit(): void {

    this.subscription = this.toastService.toast$.subscribe(toast => {

      this.showToast(toast);

    });

  }

  private showToast(toast: Toast): void {

    this.clearTimers();

    this.toast = toast;

    this.visible = true;

    this.progress = 100;

    const duration = toast.duration ?? 4000;

    const interval = 20;

    const step = (100 * interval) / duration;

    this.intervalId = setInterval(() => {

      this.progress -= step;

      if (this.progress < 0) {
        this.progress = 0;
      }

    }, interval);

    this.timeoutId = setTimeout(() => {

      this.close();

    }, duration);

  }

  close(): void {

    this.clearTimers();

    this.visible = false;

    this.toast = null;

  }

  private clearTimers(): void {

    if (this.intervalId) {
      clearInterval(this.intervalId);
    }

    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }

  }

  ngOnDestroy(): void {

    this.subscription?.unsubscribe();

    this.clearTimers();

  }

}