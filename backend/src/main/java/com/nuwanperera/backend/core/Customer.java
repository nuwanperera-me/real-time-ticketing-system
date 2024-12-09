package com.nuwanperera.backend.core;

import java.util.Random;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;

import com.nuwanperera.backend.utils.LogAppender;

public class Customer implements Runnable {
  private final int customerId;
  private int retrievalInterval;

  private final Logger logger;

  private volatile boolean isRunning = true;

  public Customer(int retrievalInterval) {
    this.customerId = new Random().nextInt(100_000);
    setRetrievalInterval(retrievalInterval);

    logger = (Logger) LogManager.getRootLogger();
    logger.addAppender(LogAppender.getInstance());
    logger.info(
        String.format("New customer created with id %d and retrieval interval %d", customerId, retrievalInterval));
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
        logger.info("Customer thread interrupted.");
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
        logger.error("Customer thread interrupted.");
      }
    }
  }

  public synchronized boolean getRunningStatus() {
    return isRunning;
  }

  public synchronized void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
    logger.info(String.format("Customer %d running status set to %s", customerId, isRunning));
    if (isRunning) {
      notifyAll();
    }
  }
}
