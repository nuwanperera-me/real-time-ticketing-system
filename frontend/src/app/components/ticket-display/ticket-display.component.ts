import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  Ticket,
  TicketStoreService,
} from '../../core/store/ticket-store.service';
import { TicketService } from '../../core/services/ticket.service';
import {
  Configuration,
  ConfigurationStoreService,
} from '../../core/store/configuration-store.service';

@Component({
  selector: 'app-ticket-display',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './ticket-display.component.html',
})
export class TicketDisplayComponent implements OnInit {
  tickets: Ticket[] = [];

  config: Configuration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketsCapacity: 0,
    runningStatus: false,
  };

  constructor(
    private ticketStore: TicketStoreService,
    private ticketService: TicketService,
    private configStore: ConfigurationStoreService
  ) {}

  ngOnInit() {
    this.ticketStore.tickets$.subscribe((tickets) => {
      this.tickets = tickets;
    });

    this.configStore.config$.subscribe((config) => {
      if (config) this.config = { ...config };
    });

    this.ticketService.fetchTickets();
    this.ticketService.startPolling();
  }
}
