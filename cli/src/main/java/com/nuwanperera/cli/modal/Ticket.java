package com.nuwanperera.cli.modal;

public class Ticket {
  private int ticketId;
  private int vendorId;

  public int getTicketId() {
    return ticketId;
  }

  public int getVendorId() {
    return vendorId;
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "ticketId=" + ticketId +
        '}';
  }
}