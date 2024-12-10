import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import {
  Configuration,
  ConfigurationStoreService,
} from '../store/configuration-store.service';

@Injectable({
  providedIn: 'root',
})
export class ConfigurationService {
  private client: AxiosInstance;
  private pollingInterval: any = null;

  constructor(private configurationStore: ConfigurationStoreService) {
    this.client = axios.create({
      baseURL: 'http://localhost:8080/api/config',
      timeout: 5000,
    });
  }

  async getConfiguration() {
    try {
      const response = await this.client.get('');
      this.configurationStore.updateConfiguration(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  async updateConfiguration(configuration: Configuration) {
    try {
      const response = await this.client.patch('', {
        status: configuration.runningStatus,
        ticket_release_rate: configuration.ticketReleaseRate,
        customer_retrieval_rate: configuration.customerRetrievalRate,
        max_tickets_capacity: configuration.maxTicketsCapacity,
        total_tickets: configuration.totalTickets,
      });
      this.configurationStore.updateConfiguration(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    if (this.pollingInterval) return; 
    this.pollingInterval = setInterval(() => this.getConfiguration(), interval);
  }

  stopPolling() {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
      this.pollingInterval = null;
    }
  }
}
