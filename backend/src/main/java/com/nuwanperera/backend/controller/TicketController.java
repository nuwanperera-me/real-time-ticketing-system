package com.nuwanperera.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuwanperera.backend.core.Ticket;
import com.nuwanperera.backend.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @GetMapping
  public ResponseEntity<List<Ticket>> getTickets() {
    return ResponseEntity.ok(ticketService.getTickets());
  }
}