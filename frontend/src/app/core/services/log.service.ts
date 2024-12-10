import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import { LogStoreService } from '../store/log-store.service';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private client: AxiosInstance;

  constructor(private logStore: LogStoreService) {
    this.client = axios.create({
      baseURL: 'http://localhost:8080/api/logs',
      timeout: 5000,
    });
  }

  async getLogs() {
    try {
      const response = await this.client.get('');
      this.logStore.updateLogs(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => {
      this.getLogs();
    }, interval);
  }
}
