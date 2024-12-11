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

import com.nuwanperera.backend.core.Customer;
import com.nuwanperera.backend.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping
  public ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok(customerService.getCustomers());
  }

  @PostMapping
  public ResponseEntity<Object> addCustomer(@RequestBody HashMap<String, Object> request) {
    int retrievalInterval;
    if (request.containsKey("retrieval_interval")) {
      try {
        retrievalInterval = (int) request.get("retrieval_interval");
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", "retrieval_interval should be an integer"));
      }
    } else {
      return ResponseEntity.badRequest().body(Map.of("error", "retrieval_interval is required"));
    }
    Customer customer = customerService.addCustomer(retrievalInterval);
    return ResponseEntity.status(HttpStatus.CREATED).body(customer);
  }

  @PatchMapping("/{customerId}")
  public ResponseEntity<Object> updateRunningStatus(@PathVariable("customerId") int customerId,
      @RequestBody HashMap<String, Object> request) {
    boolean status;
    Customer customer;
    if (!request.containsKey("status")) {
      return ResponseEntity.badRequest().body(Map.of("error", "Running status is required"));
    }

    try {
      status = (boolean) request.get("status");
      customer = customerService.updateRunningStatus(customerId, status);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", "Invalid running status"));
    }
    return ResponseEntity.ok(customer);
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") int customerId) {
    Customer customer;
    try {
      customer = customerService.removeCustomer(customerId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(customer);
  }
}
