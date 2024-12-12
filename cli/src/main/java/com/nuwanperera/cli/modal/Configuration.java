package com.nuwanperera.cli.modal;

public class Configuration {
  private boolean runningStatus = true;

  private int totalTickets;
  private int ticketReleaseRate;
  private int customerRetrievalRate;
  private int maxTicketsCapacity;

  public boolean getRunningStatus() {
    return runningStatus;
  }

  public void setRunningStatus(boolean runningStatus) {
    this.runningStatus = runningStatus;
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

  public int getCustomerRetrievalRate() {
    return customerRetrievalRate;
  }

  public void setCustomerRetrievalRate(int customerRetrievalRate) {
    if (customerRetrievalRate < 0) {
      throw new IllegalArgumentException("Customer retrival rate cannot be negative");
    }
    this.customerRetrievalRate = customerRetrievalRate;
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

  @Override
  public String toString() {
    return "Configuration{" +
        "systemStatus=" + runningStatus +
        ", totalTickets=" + totalTickets +
        ", ticketReleaseRate=" + ticketReleaseRate +
        ", customerRetrievalRate=" + customerRetrievalRate +
        ", maxTicketsCapacity=" + maxTicketsCapacity +
        '}';
  }

}
