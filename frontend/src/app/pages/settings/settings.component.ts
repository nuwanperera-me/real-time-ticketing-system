import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  Configuration,
  ConfigurationStoreService,
} from '../../core/store/configuration-store.service';
import { ConfigurationService } from '../../core/services/configuration.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  config: Configuration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketsCapacity: 0,
    runningStatus: false,
  };

  isEditing: boolean = false;

  constructor(
    private configStore: ConfigurationStoreService,
    private configService: ConfigurationService
  ) {}

  ngOnInit(): void {
    this.configStore.config$.subscribe((config) => {
      this.config = config;
    });

    this.configService.stopPolling();
  }

  ngOnDestroy(): void {
    this.configService.startPolling();
  }

  saveConfiguration() {
    console.log(this.config);
    this.configService.updateConfiguration(this.config);
  }
}
