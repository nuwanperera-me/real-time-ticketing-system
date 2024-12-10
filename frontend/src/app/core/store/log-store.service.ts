import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LogStoreService {
  private logsSubject = new BehaviorSubject<string[]>([]);
  logs$ = this.logsSubject.asObservable();

  updateLogs(logs: string[]) {
    this.logsSubject.next(logs);
  }

  getLogs() {
    return this.logsSubject.value;
  }
}
