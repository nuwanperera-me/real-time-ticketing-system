import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import { TicketStoreService } from '../store/ticket-store.service';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private client: AxiosInstance;

  constructor(private ticketStore: TicketStoreService) {
    this.client = axios.create({
      baseURL: 'http://localhost:8080/api/tickets',
      timeout: 5000,
    });
  }

  async getAllTickets() {
    try {
      const response = await this.client.get('');
      this.ticketStore.updateTickets(response.data);
    } catch (error: any) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => {
      this.getAllTickets();
    }, interval);
  }
}
