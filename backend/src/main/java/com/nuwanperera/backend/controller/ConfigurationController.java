package com.nuwanperera.backend.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuwanperera.backend.config.Configuration;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {

	@GetMapping
	public Configuration getConfiguration() {
		return Configuration.getInstance();
	}

	@PatchMapping
	public ResponseEntity<Object> updateConfiguration(@RequestBody HashMap<String, Object> request) {
		Configuration config = Configuration.getInstance();

		if (request.containsKey("status")) {
			config.setRunningStatus((boolean) request.get("status"));
		}
		if (request.containsKey("total_tickets")) {
			int totalTickets;
			try {
				totalTickets = (int) request.get("total_tickets");
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("Invalid total_tickets value");
			}
			config.setTotalTickets(totalTickets);
		}
		if (request.containsKey("ticket_release_rate")) {
			int ticketReleaseRate;
			try {
				ticketReleaseRate = (int) request.get("ticket_release_rate");
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("Invalid ticket_release_rate value");
			}
			config.setTicketReleaseRate(ticketReleaseRate);
		}

		if (request.containsKey("customer_retrieval_rate")) {
			int customerRetrievalRate;
			try {
				customerRetrievalRate = (int) request.get("customer_retrieval_rate");
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("Invalid customer_retrieval_rate value");
			}
			config.setCustomerRetrievalRate(customerRetrievalRate);
		}

		if (request.containsKey("max_tickets_capacity")) {
			int maxTicketsCapacity;
			try {
				maxTicketsCapacity = (int) request.get("max_tickets_capacity");
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("Invalid max_tickets_capacity value");
			}
			config.setMaxTicketsCapacity(maxTicketsCapacity);
		}

		return ResponseEntity.ok(config);
	}
}
