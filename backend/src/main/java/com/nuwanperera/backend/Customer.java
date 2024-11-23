package com.nuwanperera.backend;

import java.util.Random;

public class Customer implements Runnable {
  private final int id;
  private int retrievalInterval;

  public Customer(int retrievalInterval) {
    this.id = new Random().nextInt(100_000);
    this.retrievalInterval = retrievalInterval;
  }

  @Override
  public void run() {
    while (true) {
      TicketPool.getInstance().removeTicket(this.id);
      try {
        Thread.sleep(retrievalInterval);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
    }
  }

  public int getId() {
    return id;
  }

  public int getRetrievalInterval() {
    return retrievalInterval;
  }

  public void setRetrievalInterval(int retrievalInterval) {
    this.retrievalInterval = retrievalInterval;
  }
}
