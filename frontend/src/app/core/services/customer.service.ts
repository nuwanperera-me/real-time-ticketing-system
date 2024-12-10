import { Injectable } from '@angular/core';
import { CustomerStoreService } from '../store/customer-store.service';
import httpClient from '../http/http-client';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  constructor(private customerStore: CustomerStoreService) {}

  async getAllCustomers() {
    const response = await httpClient.get('/customers');
    this.customerStore.setConfiguration(response.data);
    return response.data;
  }

  async updateCustomerRunningStatus(
    customerId: number,
    runningStatus: boolean
  ) {
    try {
      const response = await httpClient.patch(`/customers/${customerId}`, {
        status: runningStatus,
      });
      console.log(response.data);
      this.getAllCustomers();
    } catch (error) {
      console.error(error);
    }
  }

  async createCustomer({ retrievalInterval }: { retrievalInterval: number }) {
    try {
      const response = await httpClient.post('/customers', {
        retrieval_interval: retrievalInterval,
      });
      this.getAllCustomers();
    } catch (error) {
      console.error(error);
    }
  }

  async deleteCustomer(customerId: number) {
    try {
      await httpClient.delete(`/customers/${customerId}`);
      this.getAllCustomers();
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => this.getAllCustomers(), interval);
  }
}
