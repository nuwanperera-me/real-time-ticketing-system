package com.nuwanperera.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
  private static TicketPool instance;

  private List<Ticket> tickets = Collections.synchronizedList(new ArrayList<Ticket>());

  private int totalTickets;
  // private int maxTicketPoolSize = Configuration.getInstance().getMaxTicketsCapacity();

  private TicketPool() {}

  public static TicketPool getInstance() {
    if (instance == null) {
      instance = new TicketPool();
    }
    return instance;
  }

  public synchronized void addTicket(Ticket ticket) {
    if (tickets.size() < Configuration.getInstance().getMaxTicketsCapacity()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
      System.out.printf("Adding ticket %d to the pool.%n", ticket.getId());
      tickets.add(ticket);
      notifyAll();
    } else {
      System.out.printf("Ticket pool is full. Cannot add ticket %d.%n", ticket.getId());
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
    }
  }

  public synchronized void removeTicket(int customerId) {
    if (tickets.isEmpty()) {
      System.out.println("Ticket pool is empty. Cannot remove ticket.");
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
    } else {
      System.out.printf("Removing ticket %d from the pool by customer %d.%n", tickets.get(0).getId(), customerId);
      tickets.remove(0);
      notifyAll();
    }
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public int getTotalTickets() {
    return totalTickets;
  }

}
