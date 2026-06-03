import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler
} from '@angular/common/http';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  // intercept(req: any, next: any) {

  //   const token = localStorage.getItem('token');

  //   if (token) {
  //     req = req.clone({
  //       setHeaders: {
  //         Authorization: `Bearer ${token}`
  //       }
  //     });
  //   }

  //   return next.handle(req);
  // }
  intercept(req: HttpRequest<any>, next: HttpHandler) {
  const cloned = req.clone({
    withCredentials: true
  });

  return next.handle(cloned);
}
}