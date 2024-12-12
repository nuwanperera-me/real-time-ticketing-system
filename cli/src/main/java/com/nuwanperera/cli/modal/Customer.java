package com.nuwanperera.cli.modal;

public class Customer {
  private int customerId;
  private int retrievalInterval;

  private boolean isRunning;

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

  public boolean getRunningStatus() {
    return isRunning;
  }

  public void setRunningStatus(boolean running) {
    isRunning = running;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "customerId=" + customerId +
        ", retrievalInterval=" + retrievalInterval +
        ", isRunning=" + isRunning +
        '}';
  }

}
