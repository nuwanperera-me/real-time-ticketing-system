package com.nuwanperera.backend.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.nuwanperera.backend.core.Customer;

@Service
public class CustomerService {

	private final ConcurrentHashMap<Integer, Thread> customerThreads = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Integer, Customer> customers = new ConcurrentHashMap<>();

	public List<Customer> getCustomers() {
		return customers.values().stream().toList();
	}

	public Customer getCustomer(int customerId) {
		return customers.get(customerId);
	}

	public Customer addCustomer(int retrievalInterval) {
		Customer customer = new Customer(retrievalInterval);
		Thread customerThread = new Thread(customer, "CT-" + customer.getCustomerId());

		customers.put(customer.getCustomerId(), customer);
		customerThreads.put(customer.getCustomerId(), customerThread);
		customerThread.start();

		return customer;
	}

	public Customer removeCustomer(int customerId) {
		Customer customer = customers.get(customerId);
		if (customer == null) {
			throw new IllegalArgumentException("Customer with id " + customerId + " not found.");
		}
		customer.setRunningStatus(false);
		Thread thread = customerThreads.get(customerId);
		if (thread != null)
			thread.interrupt();
		customers.remove(customerId);
		customerThreads.remove(customerId);
		return customer;
	}

	public Customer updateRunningStatus(int customerId, boolean runningStatus) {
		Customer customer = customers.get(customerId);
		if (customer == null) {
			throw new IllegalArgumentException(String.format("Customer with id %d not found.", customerId));
		}
		customer.setRunningStatus(runningStatus);
		return customer;
	}
}
