package com.nuwanperera.backend.core;

import java.util.Random;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;

import com.nuwanperera.backend.utils.LogAppender;

public class Vendor implements Runnable {
  private TicketPool ticketPool = TicketPool.getInstance();
  private Logger logger;

  private final int vendorId;
  private int ticketsPerRelease;
  private int releaseInterval;

  private volatile boolean isRunning = true;

  public Vendor(int ticketsPerRelease, int releaseInterval) {
    this.vendorId = new Random().nextInt(100_000);
    setTicketsPerRelease(ticketsPerRelease);
    setReleaseInterval(releaseInterval);

    logger = (Logger) LogManager.getRootLogger();
    logger.addAppender(LogAppender.getInstance());
    logger.info(String.format("Vendor %d created with %d tickets per release and %d release interval.", vendorId,
        ticketsPerRelease, releaseInterval));
  }

  @Override
  public void run() {
    while (true) {
      try {
        for (int i = 0; i < ticketsPerRelease; i++) {
          waitIfVendorNotRunning();
          ticketPool.addTicket(this);
        }
        Thread.sleep(releaseInterval * 1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        logger.info(String.format("Vendor %d interrupted.", vendorId));
        break;
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
        logger.error("Vendor {} interrupted.", vendorId);
        break;
      }
    }
  }

  public synchronized boolean getRunningStatus() {
    return isRunning;
  }

  public synchronized void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
    logger.info(String.format("Vendor %d running status set to %s.", vendorId, isRunning));
    if (isRunning) {
      notifyAll();
    }
  }
}