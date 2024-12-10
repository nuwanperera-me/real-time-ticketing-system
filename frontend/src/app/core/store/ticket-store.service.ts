import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Ticket {
  ticketId: number;
  vendorId: number;
}

@Injectable({
  providedIn: 'root'
})
export class TicketStoreService {
  private ticketsSubject = new BehaviorSubject<Ticket[]>([]);
  tickets$ = this.ticketsSubject.asObservable()

  updateTickets(tickets: Ticket[]) {
    this.ticketsSubject.next(tickets);
  }

  getTickets() {
    return this.ticketsSubject.value;
  }
}
