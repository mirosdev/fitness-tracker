import {AuthData} from './auth-data.model';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SignUpData} from './signup-data.model';
import {Observable} from 'rxjs';
import * as Auth from './auth.actions';
import * as fromRoot from '../app.reducer';
import {Store} from '@ngrx/store';

const httpOptions = {
  headers: new HttpHeaders({'No-Auth': 'True', 'Content-Type': 'application/json' })
};

export const TOKEN = 'token';

@Injectable()
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/users/login';
  private signupUrl = 'http://localhost:8080/api/users/register';

  constructor(private router: Router,
              private httpClient: HttpClient,
              private store: Store<fromRoot.State>) {}

  attemptAuth(authData: AuthData): Observable<any> {
    return this.httpClient.post<any>(this.loginUrl, authData, httpOptions);
  }

  signUp(signUpData: SignUpData) {
    return this.httpClient.post(this.signupUrl, signUpData, httpOptions);
  }

  logout() {
    localStorage.removeItem(TOKEN);
    this.store.dispatch(new Auth.SetUnauthenticated());
    this.router.navigate(['/login']);
  }
}
