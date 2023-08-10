import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Observable } from 'rxjs';
import { Question } from '../model/question.interface';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  questions$!: Observable<Question[]>;
  questionNumber = 0;

  constructor(private service: ApiService) {}

  ngOnInit() {
    this.questions$ = this.service.questions('world', 1);
  }
}
