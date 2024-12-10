import { Component, OnInit } from '@angular/core';
import {
  Vendor,
  VendorStoreService,
} from '../../core/store/vendor-store.service';
import { VendorService } from '../../core/services/vendor.service';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

type newVendorProps = {
  ticketsPerRelease: number;
  releaseInterval: number;
};

@Component({
  selector: 'app-vendors',
  standalone: true,
  imports: [NgIf, NgFor, NgClass, FormsModule],
  templateUrl: './vendors.component.html',
})
export class VendorsComponent implements OnInit {
  vendors: Vendor[] = [];
  newVendor: newVendorProps = {
    ticketsPerRelease: 0,
    releaseInterval: 0,
  };

  isModelOpen:boolean = false;

  constructor(
    private vendorService: VendorService,
    private vendorStore: VendorStoreService
  ) {}

  ngOnInit(): void {
    this.vendorStore.vendors$.subscribe((vendors) => {
      this.vendors = vendors;
    });
    this.vendorService.getAllVendors();
    this.vendorService.startPolling();
  }

  toggleVendorRunningStatus(vendor: Vendor) {
    this.vendorService.updateVendorRunningStatus(
      vendor.vendorId,
      !vendor.runningStatus
    );
  }

  deleteVendor(vendor: Vendor) {
    this.vendorService.deleteVendor(vendor.vendorId);
  }

  addVendor() {
    this.vendorService.createVendor(this.newVendor);
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
    this.newVendor = {
      ticketsPerRelease: 0,
      releaseInterval: 0,
    };
  }
}
