import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import { LogStoreService } from '../store/log-store.service';
import httpClient from '../http/http-client';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  constructor(private logStore: LogStoreService) {}

  async fetchLogs() {
    try {
      const response = await httpClient.get('/logs');
      this.logStore.setLogs(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => {
      this.fetchLogs();
    }, interval);
  }
}
