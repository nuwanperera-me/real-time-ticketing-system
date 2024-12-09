package com.nuwanperera.backend.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.nuwanperera.backend.core.Vendor;
@Service
public class VendorService {

  private final ConcurrentHashMap<Integer, Thread> vendorThreads = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<Integer, Vendor> vendors = new ConcurrentHashMap<>();

  public List<Vendor> getVendors() {
    return vendors.values().stream().toList();
  }

  public Vendor addVendor(int ticketsPerRelease, int releaseInterval) {
    Vendor vendor = new Vendor(ticketsPerRelease, releaseInterval);
    Thread vendorThread = new Thread(vendor);
    vendors.put(vendor.getVendorId(), vendor);
    vendorThreads.put(vendor.getVendorId(), vendorThread);
    vendorThread.start();
    return vendor;
  }

  public Vendor removeVendor(int vendorId) {
    Vendor vendor = vendors.get(vendorId);
    if (vendor == null) {
      throw new IllegalArgumentException("Vendor with id " + vendorId + " not found.");
    }
    vendor.setRunningStatus(false);
    Thread thread = vendorThreads.get(vendorId);
    if (thread != null)
      thread.interrupt();
    vendors.remove(vendorId);
    vendorThreads.remove(vendorId);
    return vendor;
  }

  public Vendor updateRunningStatus(int vendorId, boolean runningStatus) {
    Vendor vendor = vendors.get(vendorId);
    if (vendor == null) {
      throw new IllegalArgumentException("Vendor with id " + vendorId + " not found.");
    }
    vendor.setRunningStatus(runningStatus);
    return vendor;
  }
}
