package com.nuwanperera.backend;

import java.util.Random;

public class Ticket {
  private final int ticketId;
  private final Vendor vendor;

  public Ticket(Vendor vendor) {
    this.ticketId = new Random().nextInt(100_000);
    this.vendor = vendor;
  }

  public int getTicketId() {
    return ticketId;
  }

  public int getVendorId() {
    return vendor.getVendorId();
  }
}
