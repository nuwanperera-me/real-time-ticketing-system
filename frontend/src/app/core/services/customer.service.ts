import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import { CustomerStoreService } from '../store/customer-store.service';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private client: AxiosInstance;

  constructor(private customerStore: CustomerStoreService) {
    this.client = axios.create({
      baseURL: 'http://localhost:8080/api/customers',
      timeout: 5000,
    });
  }

  async getAllCustomers() {
    const response = await this.client.get('');
    this.customerStore.updateCustomers(response.data);
    return response.data;
  }

  async updateCustomerRunningStatus(
    customerId: number,
    runningStatus: boolean
  ) {
    try {
      const response = await this.client.patch(`/${customerId}`, {
        status: runningStatus,
      });
      console.log(response.data);
      this.getAllCustomers();
    } catch (error) {
      console.error(error);
    }
  }

  async addCustomer({ retrievalInterval }: { retrievalInterval: number }) {
    try {
      const response = await this.client.post('', {
        retrieval_interval: retrievalInterval,
      });
      this.getAllCustomers();
    } catch (error) {
      console.error(error);
    }
  }

  async deleteCustomer(customerId: number) {
    try {
      await this.client.delete(`/${customerId}`);
      this.getAllCustomers();
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => this.getAllCustomers(), interval);
  }
}
