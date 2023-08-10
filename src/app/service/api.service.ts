import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, of } from 'rxjs';
import { Question } from '../model/question.interface';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private _questions = new BehaviorSubject<Question[]>([]);

  constructor(private http: HttpClient) {}

  questions(category: string, level: number): Observable<Question[]> {
    return this.loadQuestion(category).pipe(
      map((allQuestion) => {
        const shuffleQuestion = this.shuffleQuestion(allQuestion);
        const { pre, next } = this.levelQuestion(level);
        return shuffleQuestion.splice(pre, next);
      })
    );
  }

  private levelQuestion(level: number) {
    let pre = 0;
    let next = 5;
    let start = 0;
    while (start < level) {
      const step = next;
      next = next + pre;
      pre = step;

      start++;
    }
    return { pre, next };
  }

  refreshLevel(): Observable<Question[]> {
    return of(this.shuffleQuestion(this._questions.value));
  }

  private shuffleQuestion(data: Question[]): Question[] {
    // Fisher–Yates Shuffle
    let currentIndex = data.length,
      randomIndex;

    // While there remain elements to shuffle.
    while (currentIndex != 0) {
      // Pick a remaining element.
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex--;

      // And swap it with the current element.
      [data[currentIndex], data[randomIndex]] = [
        data[randomIndex],
        data[currentIndex],
      ];
    }
    this._questions.next(data);
    return data;
  }

  /**
   * new api all based on category
   * @param category
   * @returns all chuffled questions
   */
  private loadQuestion(category: string): Observable<Question[]> {
    if (this._questions.value.length) return this._questions.asObservable();
    return this.http.get<Question[]>(
      `https://raw.githubusercontent.com/itmmckernan/triviaJSON/master/${category}.json`
    );
  }
}
