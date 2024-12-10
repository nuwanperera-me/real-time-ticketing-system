package com.nuwanperera.backend.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuwanperera.backend.core.Vendor;
import com.nuwanperera.backend.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "http://localhost:4200")
public class VendorController {

  @Autowired
  private VendorService vendorService;

  @GetMapping
  public ResponseEntity<List<Vendor>> getVendors() {
    return ResponseEntity.ok().body(vendorService.getVendors());
  }

  @PostMapping
  public ResponseEntity<Object> addVendor(@RequestBody HashMap<String, Object> request) {
    int releaseInterval;
    int ticketsPerRelease;

    if (request.containsKey("release_interval")) {
      releaseInterval = (int) request.get("release_interval");
    } else {
      return ResponseEntity.badRequest().body("release_interval is required");
    }

    if (request.containsKey("tickets_per_release")) {
      ticketsPerRelease = (int) request.get("tickets_per_release");
    } else {
      return ResponseEntity.badRequest().body("tickets_per_release is required");
    }
    Vendor vendor = vendorService.addVendor(releaseInterval, ticketsPerRelease);
    return ResponseEntity.status(HttpStatus.CREATED).body(vendor);
  }

  @PatchMapping("/{vendorId}")
  public ResponseEntity<Object> updateRunnongStatus(@PathVariable("vendorId") int vendorId,
      @RequestBody HashMap<String, Object> request) {
    boolean status;
    Vendor vendor;
    if (!request.containsKey("status")) {
      return ResponseEntity.badRequest().body("running status is required");
    }
    status = (boolean) request.get("status");
    try {
      vendor = vendorService.updateRunningStatus(vendorId, status);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

    return ResponseEntity.ok(vendor);
  }

  @DeleteMapping("/{vendorId}")
  public ResponseEntity<Object> deleteVendor(@PathVariable("vendorId") int vendorId) {
    Vendor vendor;
    try {
      vendor = vendorService.removeVendor(vendorId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(vendor);
  }
}
