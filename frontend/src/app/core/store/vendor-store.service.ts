import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Vendor {
  vendorId: number;
  ticketsPerRelease: number;
  releaseInterval: number;
  runningStatus: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class VendorStoreService {
  private vendorsSubject = new BehaviorSubject<Vendor[]>([]);
  vendors$ = this.vendorsSubject.asObservable();

  updateVendor(vendors: Vendor[]): void {
    this.vendorsSubject.next(vendors);
  }

  getVendors(): Vendor[] {
    return this.vendorsSubject.value;
  }
}
