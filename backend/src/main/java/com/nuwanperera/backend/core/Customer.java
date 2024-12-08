package com.nuwanperera.backend.core;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Customer implements Runnable {
  private final int customerId;
  private int retrievalInterval;

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private volatile boolean isRunning = true;

  public Customer(int retrievalInterval) {
    this.customerId = new Random().nextInt(100_000);
    setRetrievalInterval(retrievalInterval);
  }

  @Override
  public void run() {
    while (true) {
      waitIfCustomerNotRunning();
      TicketPool.getInstance().removeTicket(this);
      try {
        Thread.sleep(retrievalInterval * 1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.error("Thread interrupted while waiting for retrieval interval", e);
        break;
      }
    }
  }

  public int getCustomerId() {
    return customerId;
  }

  public int getRetrievalInterval() {
    return retrievalInterval;
  }

  public void setRetrievalInterval(int retrievalInterval) {
    if (retrievalInterval < 1) {
      throw new IllegalArgumentException("Retrieval interval should be greater than 0");
    }
    this.retrievalInterval = retrievalInterval;
  }

  public synchronized void waitIfCustomerNotRunning() {
    while (!isRunning) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.error("Thread interrupted while waiting for customer to start", e);
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
