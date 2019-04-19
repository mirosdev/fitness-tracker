import {Exercise} from './exercise.model';
import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {DataService} from '../services/data.service';
import {PastExercise} from './past-exercise.model';
import {UiService} from '../shared/ui.service';
import * as UI from '../shared/ui.actions';
import * as Training from './training.actions';
import * as fromTraining from './training.reducer';
import {Store} from '@ngrx/store';
import {take} from 'rxjs/operators';

@Injectable()
export class TrainingService {

  constructor(private dataService: DataService,
              private uiService: UiService,
              private store: Store<fromTraining.State>) {}

  exercisesChanged = new Subject<Exercise[]>();

  fetchAvailableExercises() {
    this.store.dispatch(new UI.StartLoading());
    this.dataService.fetchAvailableExercises().subscribe(
      exercises => {
        this.store.dispatch(new UI.StopLoading());
        this.store.dispatch(new Training.SetAvailableTrainings(exercises));
      }, () => {
        this.store.dispatch(new UI.StopLoading());
        this.uiService.showSnackbar('Fetching Exercises failed, please try again later',
          null,
          3000);
        this.exercisesChanged.next(null);
      });
  }

  startExercise(selectedId: number) {
    this.store.dispatch(new Training.StartTraining(selectedId));
  }

  completeExercise() {
    this.store.select(fromTraining.getActiveTraining).subscribe(
      ex => {
        this.addDataToDatabase({...ex, date: new Date(), state: 'completed'});
        this.store.dispatch(new Training.StopTraining());
      });
  }

  cancelExercise(progress: number) {
    this.store.select(fromTraining.getActiveTraining).pipe(take(1)).subscribe(
      ex => {
        this.addDataToDatabase({...ex,
          duration: ex.duration * (progress / 100) ,
          calories: ex.calories * (progress / 100) ,
          date: new Date(),
          state: 'cancelled'});
        this.store.dispatch(new Training.StopTraining());
      });
  }

  fetchCompletedOrCancelledExercises() {
    return this.dataService.fetchPastExercises().subscribe(
      exercises => {
        this.store.dispatch(new Training.SetFinishedTrainings(exercises));
      });
  }

  private addDataToDatabase(exercise: PastExercise) {
    exercise.id = null;
    this.dataService.savePastExercise(exercise).subscribe(
      () => {
      }, () => {
      }, () => {});
  }
}
