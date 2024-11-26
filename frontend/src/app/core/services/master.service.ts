import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ticket } from '../../components/ticket-display/ticket-display.component';

@Injectable({
  providedIn: 'root',
})
export class MasterService {
  constructor(private http: HttpClient) {}

  getTickets() {
    return this.http.get<Ticket[]>('http://localhost:8080/tickets');
  }
}
