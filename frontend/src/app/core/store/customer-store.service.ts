import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Customer {
  customerId: number;
  retrievalInterval: number;
  runningStatus: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class CustomerStoreService {
  private customersSubject = new BehaviorSubject<Customer[]>([]);
  customers$ = this.customersSubject.asObservable();

  updateCustomers(customers: Customer[]): void {
    this.customersSubject.next(customers);
  }

  getCustomers(): Customer[] {
    return this.customersSubject.value;
  }
}
