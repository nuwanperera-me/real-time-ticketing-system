package com.nuwanperera.backend;

import java.util.Random;

public class Vendor implements Runnable {
  private final int id;
  private int ticketPerRelease;
  private int releaseInterval;

  public Vendor(int ticketPerRelease, int releaseInterval) {
    this.id = new Random().nextInt(100_000);
    this.ticketPerRelease = ticketPerRelease;
    this.releaseInterval = releaseInterval;
  }

  @Override
  public void run() {
    while (true) {
      if (!Configuration.getInstance().getRunningStatus()) {
        continue;
      }
      for (int i = 0; i < ticketPerRelease; i++) {
        Ticket ticket = new Ticket(this.id);
        System.out.printf("Vendor %d is adding ticket %d%n", id, ticket.getId());
        TicketPool.getInstance().addTicket(ticket);
      }
      try {
        Thread.sleep(releaseInterval);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.printf("Vendor %d interrupted.%n", id);
      }
    }
  }

  public int getId() {
    return id;
  }

  public int getTicketPerRelease() {
    return ticketPerRelease;
  }

  public int setTicketPerRelease(int ticketPerRelease) {
    return this.ticketPerRelease = ticketPerRelease;
  }

  public int getReleaseInterval() {
    return releaseInterval;
  }

  public int setReleaseInterval(int releaseInterval) {
    return this.releaseInterval = releaseInterval;
  }
}