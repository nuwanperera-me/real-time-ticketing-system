package com.nuwanperera.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      try {
        releaseInterval = (int) request.get("release_interval");
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", "release_interval should be an integer"));
      }
    } else {
      return ResponseEntity.badRequest().body(Map.of("error", "release_interval is required"));
    }

    if (request.containsKey("tickets_per_release")) {
      try {
        ticketsPerRelease = (int) request.get("tickets_per_release");
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", "tickets_per_release should be an integer"));
      }
    } else {
      return ResponseEntity.badRequest().body(Map.of("error", "tickets_per_release is required"));
    }
    Vendor vendor = vendorService.addVendor(releaseInterval, ticketsPerRelease);
    return ResponseEntity.status(HttpStatus.CREATED).body(vendor);
  }

  @GetMapping("/{vendorId}")
  public ResponseEntity<Vendor> getVendor(@PathVariable("vendorId") int vendorId) {
    Vendor vendor;
    try {
      vendor = vendorService.getVendor(vendorId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(vendor);
  }

  @PatchMapping("/{vendorId}")
  public ResponseEntity<Object> updateRunnongStatus(@PathVariable("vendorId") int vendorId,
      @RequestBody HashMap<String, Object> request) {
    boolean status;
    Vendor vendor;
    if (!request.containsKey("status")) {
      return ResponseEntity.badRequest().body(Map.of("error", "status is required"));
    }

    try {
      status = (boolean) request.get("status");
      vendor = vendorService.updateRunningStatus(vendorId, status);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", "Invalid status value"));
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
