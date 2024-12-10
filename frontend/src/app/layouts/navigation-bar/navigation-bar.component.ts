import { Component, OnInit } from '@angular/core';
import { NgIf, NgFor, NgClass } from '@angular/common';
import {
  Configuration,
  ConfigurationStoreService,
} from '../../core/store/configuration-store.service';
import { ConfigurationService } from '../../core/services/configuration.service';

export interface NavigationLinkProps {
  title: string;
  link: string;
}
@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [NgIf, NgFor, NgClass],
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

  isSideBarOpen = false;

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

    this.configService.fetchConfiguration();
    this.configService.startPolling();
  }

  toggleRunningStatus() {
    this.configService.updateRunningStatus(!this.config.runningStatus);
    this.configService.fetchConfiguration();
  }

  handleToggleSideBar() {
    this.isSideBarOpen = !this.isSideBarOpen;
  }
}
