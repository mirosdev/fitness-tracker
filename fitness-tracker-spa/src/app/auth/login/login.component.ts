import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService, TOKEN} from '../auth.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {UiService} from '../../shared/ui.service';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../app.reducer';
import * as Auth from '../auth.actions';
import * as UI from '../../shared/ui.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  isLoading$: Observable<boolean>;

  constructor(private authService: AuthService,
              private router: Router,
              private uiService: UiService,
              private store: Store<fromRoot.State>) { }

  ngOnInit() {
    this.isLoading$ = this.store.select(fromRoot.getIsLoading);

    localStorage.removeItem(TOKEN);
    this.loginForm = new FormGroup({
      email: new FormControl('', {validators: [Validators.required, Validators.email]}),
      password: new FormControl('', {validators: [Validators.required]})
    });
  }

  onSubmit() {
    this.store.dispatch(new UI.StartLoading());
    this.authService.attemptAuth({
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    })
      .subscribe(
      response => {
        this.store.dispatch(new UI.StopLoading());
        this.store.dispatch(new Auth.SetAuthenticated());
        localStorage.setItem(TOKEN, response.token.substring(7));
        this.router.navigate(['/training']);
      }, error => {
          this.store.dispatch(new UI.StopLoading());
          this.uiService.showSnackbar(error, null, 3000);
      }
    );
  }
}
