import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Configuration {
  totalTickets: number;
  ticketReleaseRate: number;
  customerRetrievalRate: number;
  maxTicketsCapacity: number;
  runningStatus: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class ConfigurationStoreService {
  private configSubject = new BehaviorSubject<Configuration>({
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketsCapacity: 0,
    runningStatus: false,
  });
  config$ = this.configSubject.asObservable();

  updateConfiguration(config: Configuration) {
    this.configSubject.next(config);
  }

  getConfiguration(): Configuration {
    return this.configSubject.value;
  }
}
