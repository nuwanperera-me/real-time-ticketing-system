package com.nuwanperera.backend.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuwanperera.backend.config.Configuration;

@RestController
@RequestMapping("/api/config")
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

		return ResponseEntity.ok(config);
	}
}
