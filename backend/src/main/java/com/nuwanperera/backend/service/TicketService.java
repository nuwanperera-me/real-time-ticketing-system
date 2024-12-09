package com.nuwanperera.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nuwanperera.backend.core.Ticket;
import com.nuwanperera.backend.core.TicketPool;

@Service
public class TicketService {
  private TicketPool ticketPool = TicketPool.getInstance();

  public List<Ticket> getTickets() {
    return ticketPool.getTickets();
  }
}
