package com.nuwanperera.backend.core;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vendor implements Runnable {
  private TicketPool ticketPool = TicketPool.getInstance();

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private final int vendorId;
  private int ticketsPerRelease;
  private int releaseInterval;

  private volatile boolean isRunning = true;

  public Vendor(int ticketsPerRelease, int releaseInterval) {
    this.vendorId = new Random().nextInt(100_000);
    setTicketsPerRelease(ticketsPerRelease);
    setReleaseInterval(releaseInterval);
  }

  @Override
  public void run() {
    while (true) {
      for (int i = 0; i < ticketsPerRelease; i++) {
        waitIfVendorNotRunning();
        ticketPool.addTicket(this);
      }
      try {
        Thread.sleep(releaseInterval * 1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.error("Vendor {} interrupted.", vendorId, e);
      }
    }
  }

  public int getVendorId() {
    return vendorId;
  }

  public int getTicketsPerRelease() {
    return ticketsPerRelease;
  }

  public int setTicketsPerRelease(int ticketPerRelease) {
    if (ticketPerRelease < 0) {
      throw new IllegalArgumentException("Ticket per release cannot be negative.");
    }
    return this.ticketsPerRelease = ticketPerRelease;
  }

  public int getReleaseInterval() {
    return releaseInterval;
  }

  public int setReleaseInterval(int releaseInterval) {
    if (releaseInterval < 0) {
      throw new IllegalArgumentException("Release interval cannot be negative.");
    }
    return this.releaseInterval = releaseInterval;
  }

  public synchronized void waitIfVendorNotRunning() {
    while (!isRunning) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.printf("Vendor %d interrupted.%n", vendorId);
        LOGGER.error("Vendor {} interrupted.", vendorId, e);
      }
    }
  }

  public synchronized boolean getRunningStatus() {
    return isRunning;
  }

  public synchronized void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
    if (isRunning) {
      notifyAll();
    }
  }
}