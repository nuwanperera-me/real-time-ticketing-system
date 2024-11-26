import { NgFor, NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MasterService } from '../../core/services/master.service';

export interface Ticket {
  id: number;
  vendorId: number;
}

@Component({
  selector: 'app-ticket-display',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './ticket-display.component.html',
})
export class TicketDisplayComponent {
  masterService = inject(MasterService);

  tickets: Ticket[] = [];

  constructor() {
    this.getTickets();

    setInterval(() => {
      this.getTickets();
    }, 10);
  }

  getTickets() {
    this.masterService.getTickets().subscribe((data) => {
      this.tickets = data;
      console.log(data);
    });
  }
}
