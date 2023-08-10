import { Pipe, PipeTransform } from '@angular/core';
import {
  Observable,
  catchError,
  from,
  isObservable,
  map,
  of,
  startWith,
} from 'rxjs';
import { PayloadOutput } from './http.interface';

@Pipe({
  name: 'payload',
})
export class PayloadPipe implements PipeTransform {
  transform<T>(
    obj: Observable<T> | Promise<T> | T
  ): Observable<PayloadOutput<T | any>>;
  transform<T>(obj: Observable<T> | Promise<T> | T) {
    return attachProgress(obj);
  }
}

function attachProgress<T>(
  obj: Observable<T> | Promise<T> | T,
  value?: T
): Observable<PayloadOutput<T>> {
  if (obj instanceof Observable || obj instanceof Promise) {
    return (obj instanceof Observable ? obj : from(obj)).pipe(
      map((v) => ({ value: v })),
      startWith({ loading: true, value }),
      catchError((error) => of({ error, value }))
    );
  }
  return of({ value: obj, loading: false, error: null });
}
