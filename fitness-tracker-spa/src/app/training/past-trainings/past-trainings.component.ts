import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Exercise} from '../exercise.model';
import {TrainingService} from '../training.service';
import * as fromTraining from '../training.reducer';
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-past-trainings',
  templateUrl: './past-trainings.component.html',
  styleUrls: ['./past-trainings.component.css']
})
export class PastTrainingsComponent implements OnInit, AfterViewInit {

  displayedColumns = ['date', 'name', 'duration', 'calories', 'state'];
  dataSource: MatTableDataSource<Exercise>;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private trainingService: TrainingService,
              private store: Store<fromTraining.State>) { }

  ngOnInit() {

    setTimeout(() => {
      this.store.select(fromTraining.getFinishedExercises).subscribe(
        exercises => {
          this.dataSource = new MatTableDataSource(exercises);
        }, error => {
          console.log(error);
        }
      );
      this.trainingService.fetchCompletedOrCancelledExercises();
    }, 500);
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }, 700);
  }

  doFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
