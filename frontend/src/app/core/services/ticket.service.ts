import { Injectable } from '@angular/core';
import { TicketStoreService } from '../store/ticket-store.service';
import httpClient from '../http/http-client';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  constructor(private ticketStore: TicketStoreService) {}

  async fetchTickets() {
    try {
      const response = await httpClient.get('/tickets');
      this.ticketStore.setTickets(response.data);
    } catch (error: any) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => {
      this.fetchTickets();
    }, interval);
  }
}
