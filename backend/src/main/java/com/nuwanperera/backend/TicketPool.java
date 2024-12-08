package com.nuwanperera.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nuwanperera.backend.config.Configuration;

public class TicketPool {
  private static TicketPool instance;
  private Configuration configuration = Configuration.getInstance();

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
        System.out.printf("Maximum number of tickets reached. Cannot add more tickets.%n");
        return;
      }
      configuration.waitIfPaused();
      while (this.getTicketCount() >= configuration.getMaxTicketsCapacity()) {
        System.out.printf("Ticket pool is full. Vendor %d is waiting to add a ticket.%n", vendor.getVendorId());
        wait();
      }
      Thread.sleep(configuration.getTicketReleaseRate() * 1000);
      Ticket ticket = new Ticket(vendor);
      tickets.add(ticket);
      currentTotalTickets++;
      System.out.printf("Ticket %d added to the pool by vendor %d.%n", ticket.getTicketId(), vendor.getVendorId());
      notifyAll();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
  }

  public synchronized void removeTicket(Customer customer) {
    try {
      while (tickets.isEmpty()) {
        System.out.println("Ticket pool is empty. Cannot remove ticket.");
        wait();
      }
      configuration.waitIfPaused();

      Thread.sleep(configuration.getCustomerRetrievalRate() * 1000);
      tickets.remove(0);
      System.out.printf("Ticket removed from the pool by customer %d.%n", customer.getCustomerId());
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
