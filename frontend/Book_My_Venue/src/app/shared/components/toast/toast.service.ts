import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Toast } from './toast.model';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  private readonly toastSubject = new Subject<Toast>();

  readonly toast$ = this.toastSubject.asObservable();

  success(
    title: string,
    message: string,
    duration = 4000
  ): void {

    this.toastSubject.next({
      title,
      message,
      type: 'success',
      duration
    });

  }

  error(
    title: string,
    message: string,
    duration = 4000
  ): void {

    this.toastSubject.next({
      title,
      message,
      type: 'error',
      duration
    });

  }

  warning(
    title: string,
    message: string,
    duration = 4000
  ): void {

    this.toastSubject.next({
      title,
      message,
      type: 'warning',
      duration
    });

  }

  info(
    title: string,
    message: string,
    duration = 4000
  ): void {

    this.toastSubject.next({
      title,
      message,
      type: 'info',
      duration
    });

  }

}