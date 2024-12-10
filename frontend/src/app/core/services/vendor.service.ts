import { Injectable } from '@angular/core';
import axios, { AxiosInstance } from 'axios';
import { VendorStoreService } from '../store/vendor-store.service';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private client: AxiosInstance;

  constructor(private vendorStore: VendorStoreService) {
    this.client = axios.create({
      baseURL: 'http://localhost:8080/api/vendors',
      timeout: 5000,
    });
  }

  async getAllVendors() {
    try {
      const response = await this.client.get('');
      this.vendorStore.updateVendor(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  async updateVendorRunningStatus(vendorId: number, status: boolean) {
    try {
      const response = await this.client.patch(`/${vendorId}`, {
        status: status,
      });
      this.getAllVendors();
    } catch (error) {
      console.error(error);
    }
  }

  async addVendor({
    ticketsPerRelease,
    releaseInterval,
  }: {
    ticketsPerRelease: number;
    releaseInterval: number;
  }) {
    try {
      const response = await this.client.post('', {
        tickets_per_release: ticketsPerRelease,
        release_interval: releaseInterval,
      });
      this.getAllVendors();
    } catch (error) {console.error(error);}
  }

  async deleteVendor(vendorId: number) {
    try {
      await this.client.delete(`/${vendorId}`);
      this.getAllVendors();
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => this.getAllVendors(), interval);
  }
}
