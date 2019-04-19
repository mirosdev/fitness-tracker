import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material';

@Injectable()
export class UiService {

  constructor(private matSnackBar: MatSnackBar) {}

  showSnackbar(message, action, duration) {
    this.matSnackBar.open(message, action, {
      duration: duration
    });
  }
}
