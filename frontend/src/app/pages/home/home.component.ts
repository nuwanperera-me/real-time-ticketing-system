import { Component } from '@angular/core';
import { TicketDisplayComponent } from '../../components/ticket-display/ticket-display.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [TicketDisplayComponent],
  templateUrl: './home.component.html',
})
export class HomeComponent {

}
