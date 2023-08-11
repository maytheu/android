import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Observable, tap } from 'rxjs';
import { Question } from '../model/question.interface';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  questions$!: Observable<Question[]>;
  questionNumber = 0;
  nextQuestion: boolean[] = [];
  level = 1;
  totalQuestion!: number;
  levelRefresh = false;
  nextLevel = false;
  correctAnswer!:number

  constructor(private service: ApiService) {}

  ngOnInit() {
    this.questions$ = this.onLoadQuestions();
  }

  onSubmit(
    answer: string,
    originalAnswer: string,
    index: number,
    selected: boolean
  ) {
    if (selected === undefined) {
      this.nextQuestion[index] = answer === originalAnswer;
    }
  }

  onNextQuestion(index: number) {
    //check if question is within range
    if (index < this.totalQuestion - 1) this.questionNumber++;
    else {
      this.nextQuestion = [];
      this.correctAnswer = this.nextQuestion.filter((t) => t == true).length;
      const nextLevelPercent = 0.7 * this.totalQuestion;
      if (this.correctAnswer >= nextLevelPercent) {
        this.level++;
        this.nextLevel = true;
      } else this.levelRefresh = true;
    }
  }
  onRefreshLevel() {
    this.questions$ = this.onLoadQuestions().pipe(
      tap(() => (this.levelRefresh = false))
    );
  }

  onNextLevel() {
    this.questions$ = this.service
      .questions('world', this.level)
      .pipe(tap((questions) => (this.totalQuestion = questions.length)));
  }

  private onLoadQuestions() {
    return this.service.questions('world', this.level).pipe(
      tap((questions) => {
        this.questionNumber = 0;
        this.totalQuestion = questions.length;
      })
    );
  }
}
