package com.nuwanperera.backend.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuwanperera.backend.config.Configuration;

public class TicketPool {
  private static TicketPool instance;
  private Configuration configuration = Configuration.getInstance();
  private static final Logger LOGGER = LoggerFactory.getLogger(TicketPool.class);

  private volatile List<Ticket> tickets = Collections.synchronizedList(new ArrayList<Ticket>());

  private volatile static int currentTotalTickets = 0;

  private TicketPool() {
  }

  public static TicketPool getInstance() {
    if (instance == null) {
      instance = new TicketPool();
    }
    return instance;
  }

  public synchronized void addTicket(Vendor vendor) {
    try {
      if (currentTotalTickets >= configuration.getTotalTickets()) {
        LOGGER.info("Maximum number of tickets reached. Cannot add more tickets.");
        return;
      }
      configuration.waitIfPaused();
      while (this.getTicketCount() >= configuration.getMaxTicketsCapacity()) {
        LOGGER.info("Ticket pool is full. Vendor {} is waiting to add a ticket.", vendor.getVendorId());
        wait();
      }
      Thread.sleep(configuration.getTicketReleaseRate() * 1000);
      Ticket ticket = new Ticket(vendor);
      tickets.add(ticket);
      currentTotalTickets++;
      LOGGER.info(String.format("Ticket %d added to the pool by vendor %d.", ticket.getTicketId(), vendor.getVendorId()));
      notifyAll();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
  }

  public synchronized void removeTicket(Customer customer) {
    try {
      while (tickets.isEmpty()) {
        LOGGER.info("Ticket pool is empty. Cannot remove ticket.");
        wait();
      }
      configuration.waitIfPaused();

      Thread.sleep(configuration.getCustomerRetrievalRate() * 1000);
      tickets.remove(0);
      LOGGER.info(String.format("Ticket removed from the pool by customer %d.", customer.getCustomerId()));
      notifyAll();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
  }

  public synchronized int getTicketCount() {
    return tickets.size();
  }

  public synchronized int getCurrentTotalTickets() {
    return currentTotalTickets;
  }

  public synchronized List<Ticket> getTickets() {
    return tickets;
  }
}
