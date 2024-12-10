import { Injectable } from '@angular/core';
import { VendorStoreService } from '../store/vendor-store.service';
import httpClient from '../http/http-client';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  constructor(private vendorStore: VendorStoreService) {}

  async getAllVendors() {
    try {
      const response = await httpClient.get('/vendors');
      this.vendorStore.setVendors(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  async updateVendorRunningStatus(vendorId: number, status: boolean) {
    try {
      const response = await httpClient.patch(`/vendors/${vendorId}`, {
        status: status,
      });
      this.getAllVendors();
    } catch (error) {
      console.error(error);
    }
  }

  async createVendor({
    ticketsPerRelease,
    releaseInterval,
  }: {
    ticketsPerRelease: number;
    releaseInterval: number;
  }) {
    try {
      const response = await httpClient.post('/vendors', {
        tickets_per_release: ticketsPerRelease,
        release_interval: releaseInterval,
      });
      this.getAllVendors();
    } catch (error) {
      console.error(error);
    }
  }

  async deleteVendor(vendorId: number) {
    try {
      await httpClient.delete(`/vendors/${vendorId}`);
      this.getAllVendors();
    } catch (error) {
      console.error(error);
    }
  }

  startPolling(interval: number = 1000) {
    setInterval(() => this.getAllVendors(), interval);
  }
}
