import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import {
  Configuration,
  ConfigurationStoreService,
} from '../store/configuration-store.service';
import httpClient from '../http/http-client';

@Injectable({
  providedIn: 'root',
})
export class ConfigurationService {
  private pollingInterval: any = null;

  constructor(private configurationStore: ConfigurationStoreService) {}

  async fetchConfiguration() {
    try {
      const response = await httpClient.get('/config');
      this.configurationStore.setConfiguration(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  async updateConfiguration(configuration: Configuration) {
    try {
      const response = await httpClient.patch('/config', {
        ticket_release_rate: configuration.ticketReleaseRate,
        customer_retrieval_rate: configuration.customerRetrievalRate,
        max_tickets_capacity: configuration.maxTicketsCapacity,
        total_tickets: configuration.totalTickets,
      });
      this.configurationStore.setConfiguration(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  async updateRunningStatus(runningStatus: boolean) {
    try {
      const response = await httpClient.patch('/config', {
        status: runningStatus,
      });
      this.configurationStore.setConfiguration(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    if (this.pollingInterval) return;
    this.pollingInterval = setInterval(
      () => this.fetchConfiguration(),
      interval
    );
  }

  stopPolling() {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
      this.pollingInterval = null;
    }
  }
}
