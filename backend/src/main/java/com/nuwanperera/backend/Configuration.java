package com.nuwanperera.backend;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;

public class Configuration {
  private static Configuration instance;

  private static Gson gson = new Gson();

  private boolean isRunning = true;

  // private int totalTickets = 10;
  private int ticketReleaseRate;
  private int customerRetrievalRate;
  private int maxTicketsCapacity;
  private int releaseInterval;

  private Configuration() {
    // loadConfiguration("config.json");
  }

  public static Configuration getInstance() {
    if (instance == null) {
      instance = new Configuration();
      instance.loadConfiguration("config.json");
    }
    return instance;
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

  public boolean getRunningStatus() {
    return isRunning;
  }

  public void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
    saveConfiguration("config.json");
  }

  // public int getTotalTickets() {
  // return totalTickets;
  // }

  // public void setTotalTickets(int totalTickets) {
  // if (totalTickets < 0) {
  // throw new IllegalArgumentException("Total tickets cannot be negative");
  // }
  // this.totalTickets = totalTickets;
  // }

  public int getTicketReleaseRate() {
    return ticketReleaseRate;
  }

  public void setTicketReleaseRate(int ticketReleaseRate) {
    if (ticketReleaseRate < 0) {
      throw new IllegalArgumentException("Ticket release rate cannot be negative");
    }
    this.ticketReleaseRate = ticketReleaseRate;
    saveConfiguration("config.json");
  }

  public int getCustomerRetrievalRate() {
    return customerRetrievalRate;
  }

  public void setCustomerRetrievalRate(int customerRetrivalRate) {
    if (customerRetrivalRate < 0) {
      throw new IllegalArgumentException("Customer retrival rate cannot be negative");
    }
    this.customerRetrievalRate = customerRetrivalRate;
    saveConfiguration("config.json");
  }

  public int getMaxTicketsCapacity() {
    return maxTicketsCapacity;
  }

  public void setMaxTicketsCapacity(int maxTicketsCapacity) {
    if (maxTicketsCapacity < 0) {
      throw new IllegalArgumentException("Max tickets capacity cannot be negative");
    }
    this.maxTicketsCapacity = maxTicketsCapacity;
    saveConfiguration("config.json");
  }

  public int getReleaseInterval() {
    return releaseInterval;
  }

  public void setReleaseInterval(int releaseInterval) {
    if (releaseInterval < 0) {
      throw new IllegalArgumentException("Release interval cannot be negative");
    }
    this.releaseInterval = releaseInterval;
    saveConfiguration("config.json");
  }

}
