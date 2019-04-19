import {Injectable} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(
        catchError(error => {
          if (error instanceof HttpErrorResponse) {

            // if (error.status === 401) {
            //   return throwError(error.statusText);
            // }

            const apiError = error.error;
            let modalStateErrors = '';
            if (apiError && typeof apiError === 'object') {
              for (const key in apiError) {
                if (apiError[key]) {
                  modalStateErrors += apiError[key] + '\n';
                }
              }
            }
            return throwError(modalStateErrors || apiError || 'Server Error');
          }
        })
      );
  }
}

export const ErrorInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: ErrorInterceptor,
  multi: true
};
