package com.nuwanperera.backend;

public class Configuration {
  private static Configuration instance;

  private boolean isRunning = true;

  private int totalTickets = 10;
  private int ticketReleaseRate = 5;
  private int customerRetrivalRate = 5;
  private int maxTicketsCapacity = 10;
  private int releaseInterval = 1000;

  private Configuration() {
  }

  public static Configuration getInstance() {
    if (instance == null) {
      instance = new Configuration();
    }
    return instance;
  }

  public boolean getRunningStatus() {
    return isRunning;
  }

  public void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
  }

  public int getTotalTickets() {
    return totalTickets;
  }

  public void setTotalTickets(int totalTickets) {
    if (totalTickets < 0) {
      throw new IllegalArgumentException("Total tickets cannot be negative");
    }
    this.totalTickets = totalTickets;
  }

  public int getTicketReleaseRate() {
    return ticketReleaseRate;
  }

  public void setTicketReleaseRate(int ticketReleaseRate) {
    if (ticketReleaseRate < 0) {
      throw new IllegalArgumentException("Ticket release rate cannot be negative");
    }
    this.ticketReleaseRate = ticketReleaseRate;
  }

  public int getCustomerRetrivalRate() {
    return customerRetrivalRate;
  }

  public void setCustomerRetrivalRate(int customerRetrivalRate) {
    if (customerRetrivalRate < 0) {
      throw new IllegalArgumentException("Customer retrival rate cannot be negative");
    }
    this.customerRetrivalRate = customerRetrivalRate;
  }

  public int getMaxTicketsCapacity() {
    return maxTicketsCapacity;
  }

  public void setMaxTicketsCapacity(int maxTicketsCapacity) {
    if (maxTicketsCapacity < 0) {
      throw new IllegalArgumentException("Max tickets capacity cannot be negative");
    }
    this.maxTicketsCapacity = maxTicketsCapacity;
  }

  public int getReleaseInterval() {
    return releaseInterval;
  }

  public void setReleaseInterval(int releaseInterval) {
    if (releaseInterval < 0) {
      throw new IllegalArgumentException("Release interval cannot be negative");
    }
    this.releaseInterval = releaseInterval;
  }

}
