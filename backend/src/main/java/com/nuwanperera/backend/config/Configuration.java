package com.nuwanperera.backend.config;

import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;

public class Configuration {
  private static Configuration instance;

  private static Gson gson = new Gson();

  private volatile boolean isRunning = true;

  private volatile int totalTickets;
  private volatile int ticketReleaseRate;
  private volatile int customerRetrievalRate;
  private volatile int maxTicketsCapacity;

  private Configuration() {
  }

  public static Configuration getInstance() {
    if (instance == null) {
      synchronized (Configuration.class) {
        if (instance == null) {
          instance = new Configuration();
          instance.loadConfiguration("config.json");
        }
      }
    }
    return instance;
  }

  public synchronized void waitIfPaused() {
    while (!isRunning) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
    saveConfiguration("config.json");
    if (isRunning) {
      notifyAll();
    }
  }

  public synchronized boolean getRunningStatus() {
    return isRunning;
  }

  public void saveConfiguration(String filePath) {
    try {
      FileWriter writer = new FileWriter(filePath);
      gson.toJson(this, writer);
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadConfiguration(String filePath) {
    try {
      instance = gson.fromJson(new FileReader(filePath), Configuration.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // public void setRunningStatus(boolean isRunning) {
  // this.isRunning = isRunning;
  // saveConfiguration("config.json");
  // }

  public synchronized int getTotalTickets() {
    return totalTickets;
  }

  public synchronized void setTotalTickets(int totalTickets) {
    if (totalTickets < 0) {
      throw new IllegalArgumentException("Total tickets cannot be negative");
    }
    this.totalTickets = totalTickets;
    saveConfiguration("config.json");
  }

  public synchronized int getTicketReleaseRate() {
    return ticketReleaseRate;
  }

  public synchronized void setTicketReleaseRate(int ticketReleaseRate) {
    if (ticketReleaseRate < 0) {
      throw new IllegalArgumentException("Ticket release rate cannot be negative");
    }
    this.ticketReleaseRate = ticketReleaseRate;
    saveConfiguration("config.json");
  }

  public synchronized int getCustomerRetrievalRate() {
    return customerRetrievalRate;
  }

  public synchronized void setCustomerRetrievalRate(int customerRetrivalRate) {
    if (customerRetrivalRate < 0) {
      throw new IllegalArgumentException("Customer retrival rate cannot be negative");
    }
    this.customerRetrievalRate = customerRetrivalRate;
    saveConfiguration("config.json");
  }

  public synchronized int getMaxTicketsCapacity() {
    return maxTicketsCapacity;
  }

  public synchronized void setMaxTicketsCapacity(int maxTicketsCapacity) {
    if (maxTicketsCapacity < 0) {
      throw new IllegalArgumentException("Max tickets capacity cannot be negative");
    }
    this.maxTicketsCapacity = maxTicketsCapacity;
    saveConfiguration("config.json");
  }
}
