import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  constructor() { }

  private _loading = new BehaviorSubject<boolean>(false);
  public readonly loading$ = this._loading.asObservable();

  setLoading(loading: boolean) {
    this._loading.next(loading);
  }

  getLoading() {
    return this._loading.asObservable();
  }
}
