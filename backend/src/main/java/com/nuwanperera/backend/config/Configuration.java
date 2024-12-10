package com.nuwanperera.backend.config;

import java.io.FileReader;
import java.io.FileWriter;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;
import com.google.gson.Gson;

import com.nuwanperera.backend.utils.LogAppender;

public class Configuration {
  private static Configuration instance;

  private static Gson gson = new Gson();

  private static Logger logger;

  private volatile boolean systemStatus = true;

  private volatile int totalTickets;
  private volatile int ticketReleaseRate;
  private volatile int customerRetrievalRate;
  private volatile int maxTicketsCapacity;

  private Configuration() {
    logger = (Logger) LogManager.getRootLogger();
    logger.addAppender(LogAppender.getInstance());
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
    while (!systemStatus) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized void setRunningStatus(boolean systemStatus) {
    this.systemStatus = systemStatus;
    saveConfiguration("config.json");
    logger.info(String.format("System Running status is set to %s", systemStatus));
    if (systemStatus) {
      notifyAll();
    }
  }

  public synchronized boolean getRunningStatus() {
    return systemStatus;
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

  public synchronized int getTotalTickets() {
    return totalTickets;
  }

  public synchronized void setTotalTickets(int totalTickets) {
    if (totalTickets < 0) {
      throw new IllegalArgumentException("Total tickets cannot be negative");
    }
    this.totalTickets = totalTickets;
    saveConfiguration("config.json");
    logger.info(String.format("Total tickets set to %d", totalTickets));
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
    logger.info(String.format("Ticket release rate set to %d", ticketReleaseRate));
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
    logger.info(String.format("Customer retrival rate set to %d", customerRetrivalRate));
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
    // TicketPool.getInstance().notifyToResume();
    logger.info(String.format("Max tickets capacity set to %d", maxTicketsCapacity));
  }
}
