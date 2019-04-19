import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AvailableExercises} from '../training/new-training/available-exercises.model';
import {PastExercise} from '../training/past-exercise.model';
import {Exercise} from '../training/exercise.model';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private httpClient: HttpClient) { }

  fetchAvailableExercises(): Observable<AvailableExercises[]> {
    return this.httpClient.get<AvailableExercises[]>('http://localhost:8080/api/training/available');
  }

  fetchPastExercises(): Observable<Exercise[]> {
    return this.httpClient.get<Exercise[]>('http://localhost:8080/api/training/past');
  }

  savePastExercise(pastExercise: PastExercise) {
    return this.httpClient.post<PastExercise>('http://localhost:8080/api/training/save', pastExercise);
  }
}
