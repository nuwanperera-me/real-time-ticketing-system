import { NgIf, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  Configuration,
  ConfigurationStoreService,
} from '../../core/store/configuration-store.service';
import { ConfigurationService } from '../../core/services/configuration.service';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './navigation-bar.component.html',
})
export class NavigationBarComponent implements OnInit {
  config: Configuration = {
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketsCapacity: 0,
    runningStatus: false,
  };

  navigationLinks = [
    { title: 'Vendors', link: '/vendors' },
    { title: 'Customers', link: '/customers' },
    { title: 'Settings', link: '/settings' },
    { title: 'Logs', link: '/logs' },
  ];

  constructor(
    private configStore: ConfigurationStoreService,
    private configService: ConfigurationService
  ) {}

  routeIsActive(route: string): boolean {
    return window.location.pathname === route;
  }

  ngOnInit(): void {
    this.configStore.config$.subscribe((config) => {
      if (config) this.config = { ...config };
    });

    this.configService.getConfiguration();
    this.configService.startPolling();
  }

  toggleRunningStatus() {
    const { runningStatus, ...restConfig } = this.config;
    this.configService.updateConfiguration({
      runningStatus: !runningStatus,
      ...restConfig,
    });
    this.configService.getConfiguration();
  }
}
