package com.nuwanperera.backend;

import java.util.HashMap;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BackendApplication {
	HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();
	HashMap<Integer, Vendor> vendors = new HashMap<Integer, Vendor>();
	HashMap<String, Thread> threads = new HashMap<String, Thread>();

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@GetMapping("/")
	public ResponseEntity<String> home() {
		return ResponseEntity.ok("Hello World!");
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
		customers.put(customer.getId(), customer);

		Thread customerThread = new Thread(customer, "CustomerThread-" + customer.getId());
		customerThread.start();
		threads.put(customerThread.getName(), customerThread);

		System.out.printf("Created customer with id %d and retrieval interval %d%n", customer.getId(), retrievalInterval);

		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
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

		Vendor vendor = new Vendor(releaseInterval, ticketsPerRelease);
		vendors.put(vendor.getId(), vendor);
		Thread vendorThread = new Thread(vendor, "VendorThread-" + vendor.getId());
		vendorThread.start();
		threads.put(vendorThread.getName(), vendorThread);

		System.out.printf("Created vendor with id %d and release interval %d and tickets per release %d%n", vendor.getId(),
				releaseInterval, ticketsPerRelease);

		return ResponseEntity.status(HttpStatus.CREATED).body(vendor);
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
			System.out.printf("Updated total tickets to %d%n", totalTickets);
		}
		if (request.containsKey("ticket_release_rate")) {
			int ticketReleaseRate = (int) request.get("ticket_release_rate");
			config.setTicketReleaseRate(ticketReleaseRate);
			System.out.printf("Updated ticket release rate to %d%n", ticketReleaseRate);
		}

		if (request.containsKey("customer_retrieval_rate")) {
			int customerRetrievalRate = (int) request.get("customer_retrieval_rate");
			config.setCustomerRetrivalRate(customerRetrievalRate);
			System.out.printf("Updated customer retrieval rate to %d%n", customerRetrievalRate);
		}

		if (request.containsKey("max_tickets_capacity")) {
			int maxTicketsCapacity = (int) request.get("max_tickets_capacity");
			config.setMaxTicketsCapacity(maxTicketsCapacity);
			System.out.printf("Updated max tickets capacity to %d%n", maxTicketsCapacity);
		}

		if (request.containsKey("release_interval")) {
			int releaseInterval = (int) request.get("release_interval");
			config.setReleaseInterval(releaseInterval);
			System.out.printf("Updated release interval to %d%n", releaseInterval);
		}

		return ResponseEntity.ok().body(config);
	}
}