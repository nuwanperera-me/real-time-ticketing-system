import { NgIf, NgFor, NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  Customer,
  CustomerStoreService,
} from '../../core/store/customer-store.service';
import { CustomerService } from '../../core/services/customer.service';

type newCustomerProps = {
  retrievalInterval: number;
};

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [NgIf, NgFor, NgClass, FormsModule],
  templateUrl: './customers.component.html',
})
export class CustomersComponent implements OnInit {
  Customers: Customer[] = [];
  newCustomer: newCustomerProps = {
    retrievalInterval: 0,
  };

  isModelOpen: boolean = false;

  constructor(
    private customerService: CustomerService,
    private customerStore: CustomerStoreService
  ) {}

  ngOnInit(): void {
    this.customerStore.customers$.subscribe((customers) => {
      this.Customers = customers;
    });
    this.customerService.getAllCustomers();
    this.customerService.startPolling();
  }

  toggleCustomerRunningStatus(customer: Customer): void {
    this.customerService.updateCustomerRunningStatus(
      customer.customerId,
      !customer.runningStatus
    );
  }

  deleteCustomer(customer: Customer): void {
    this.customerService.deleteCustomer(customer.customerId);
  }

  addNewCustomer(): void {
    this.customerService.addCustomer(this.newCustomer);
    this.resetNewVendor();
    this.closeModel();
  }

  openModel() {
    this.isModelOpen = true;
  }

  closeModel() {
    this.isModelOpen = false;
  }

  resetNewVendor() {
    this.newCustomer = {
      retrievalInterval: 0,
    };
  }
}
