package com.nuwanperera.backend;

import java.util.HashMap;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuwanperera.backend.config.Configuration;
import com.nuwanperera.backend.core.Customer;
import com.nuwanperera.backend.core.Ticket;
import com.nuwanperera.backend.core.TicketPool;
import com.nuwanperera.backend.core.Vendor;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BackendApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BackendApplication.class);

	HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();
	HashMap<Integer, Vendor> vendors = new HashMap<Integer, Vendor>();
	HashMap<String, Thread> threads = new HashMap<String, Thread>();

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@GetMapping("/tickets")
	public List<Ticket> getTickets() {
		return TicketPool.getInstance().getTickets();
	}

	@PostMapping("/customers")
	public ResponseEntity<Object> createCustomer(@RequestBody HashMap<String, Object> request) {
		int retrievalInterval;
		if (request.containsKey("retrieval_interval")) {
			retrievalInterval = (int) request.get("retrieval_interval");
		} else {
			return ResponseEntity.badRequest().body("retrieval_interval is required");
		}

		Customer customer = new Customer(retrievalInterval);
		customers.put(customer.getCustomerId(), customer);

		Thread customerThread = new Thread(customer, "CT-" + customer.getCustomerId());
		customerThread.start();
		threads.put(customerThread.getName(), customerThread);

		LOGGER.info(String.format("Created customer with id %d and retrieval interval %d", customer.getCustomerId(),
				retrievalInterval), customerThread);

		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@PostMapping("/customers/{id}/toggle")
	public ResponseEntity<Object> toggleCustomer(@PathVariable("id") int customerId) {
		Customer customer = customers.get(customerId);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		customer.setRunningStatus(!customer.getRunningStatus());
		return ResponseEntity.ok().body(customer);
	}

	@PostMapping("/vendors")
	public ResponseEntity<Object> createVendor(@RequestBody HashMap<String, Object> request) {
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

		Vendor vendor = new Vendor(ticketsPerRelease, releaseInterval);
		vendors.put(vendor.getVendorId(), vendor);

		Thread vendorThread = new Thread(vendor, "VT-" + vendor.getVendorId());
		vendorThread.start();
		threads.put(vendorThread.getName(), vendorThread);

		LOGGER.info(String.format("Created vendor with id %d, release interval %d and tickets per release %d",
				vendor.getVendorId(), releaseInterval, ticketsPerRelease), vendorThread);

		return ResponseEntity.status(HttpStatus.CREATED).body(vendor);
	}

	@PutMapping("/vendors/{id}")
	public ResponseEntity<Object> updateVendor(@PathVariable("id") int vendorId,
			@RequestBody HashMap<String, Object> request) {
		Vendor vendor = vendors.get(vendorId);
		if (vendor == null) {
			return ResponseEntity.notFound().build();
		}

		if (request.containsKey("release_interval")) {
			vendor.setReleaseInterval((int) request.get("release_interval"));
		}
		if (request.containsKey("tickets_per_release")) {
			vendor.setTicketsPerRelease((int) request.get("tickets_per_release"));
		}
		return ResponseEntity.ok().body(vendor);
	}

	@PostMapping("/vendors/{id}/toggle")
	public ResponseEntity<Object> toggleVendor(@PathVariable("id") int vendorId) {
		Vendor vendor = vendors.get(vendorId);
		if (vendor == null) {
			return ResponseEntity.notFound().build();
		}
		vendor.setRunningStatus(!vendor.getRunningStatus());
		return ResponseEntity.ok().body(vendor);
	}

	@GetMapping("/threads")
	public HashMap<String, String> getThreadsNames() {
		HashMap<String, String> threadNames = new HashMap<String, String>();
		for (String name : threads.keySet()) {
			threadNames.put(name, threads.get(name).getState().toString());
		}
		return threadNames;
	}

	@GetMapping("/configurations")
	public Configuration getConfiguration() {
		return Configuration.getInstance();
	}

	@PostMapping("/configurations")
	public ResponseEntity<Object> updateConfiguration(@RequestBody HashMap<String, Object> request) {
		Configuration config = Configuration.getInstance();

		if (request.containsKey("status")) {
			config.setRunningStatus((boolean) request.get("status"));
		}
		if (request.containsKey("total_tickets")) {
			int totalTickets = (int) request.get("total_tickets");
			config.setTotalTickets(totalTickets);
		}
		if (request.containsKey("ticket_release_rate")) {
			int ticketReleaseRate = (int) request.get("ticket_release_rate");
			config.setTicketReleaseRate(ticketReleaseRate);
		}

		if (request.containsKey("customer_retrieval_rate")) {
			int customerRetrievalRate = (int) request.get("customer_retrieval_rate");
			config.setCustomerRetrievalRate(customerRetrievalRate);
		}

		if (request.containsKey("max_tickets_capacity")) {
			int maxTicketsCapacity = (int) request.get("max_tickets_capacity");
			config.setMaxTicketsCapacity(maxTicketsCapacity);
		}

		return ResponseEntity.ok().body(config);
	}
}