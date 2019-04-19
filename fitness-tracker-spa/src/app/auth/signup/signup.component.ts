import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthService, TOKEN} from '../auth.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {UiService} from '../../shared/ui.service';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../app.reducer';
import * as UI from '../../shared/ui.actions';
import * as Auth from '../auth.actions';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  maxDate;
  isLoading$: Observable<boolean>;

  constructor(private authService: AuthService,
              private router: Router,
              private uiService: UiService,
              private store: Store<fromRoot.State>) { }

  ngOnInit() {
    this.isLoading$ = this.store.select(fromRoot.getIsLoading);

    localStorage.removeItem(TOKEN);
    this.maxDate = new Date();
    this.maxDate.setFullYear(this.maxDate.getFullYear() - 18);
  }

  onSubmit(form: NgForm) {
    this.store.dispatch(new UI.StartLoading());
    this.authService.signUp({
      email: form.value.email,
      password: form.value.password,
      birthDate: form.value.birthDate
    })
      .subscribe(
        () => {
          this.authService.attemptAuth({
            email: form.value.email,
            password: form.value.password
          })
            .subscribe(
            loginResponse => {
              this.store.dispatch(new UI.StopLoading());
              this.store.dispatch(new Auth.SetAuthenticated());
              localStorage.setItem('token', loginResponse.token.substring(7));
              this.router.navigate(['/training']);
            }, error => {
                this.store.dispatch(new UI.StopLoading());
                this.uiService.showSnackbar(error.message, null, 3000);
            }, () => {console.log('complete login after signup subscription test'); }
          );
        }, error => {
          this.store.dispatch(new UI.StopLoading());
          this.uiService.showSnackbar(error, null, 3000);
        }
      );
  }
}
