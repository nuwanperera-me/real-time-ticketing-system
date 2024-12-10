import { Component } from '@angular/core';
import { LogDisplayComponent } from "../../components/log-display/log-display.component";

@Component({
  selector: 'app-logs-page',
  standalone: true,
  imports: [LogDisplayComponent],
  templateUrl: './logs-page.component.html',
})
export class LogsPageComponent {

}
