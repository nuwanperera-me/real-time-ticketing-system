package com.nuwanperera.backend;

import java.util.Random;

public class Ticket {
  private final int id;

  public Ticket() {
    this.id = new Random().nextInt(100_000);
  }

  public int getId() {
    return id;
  }
}
