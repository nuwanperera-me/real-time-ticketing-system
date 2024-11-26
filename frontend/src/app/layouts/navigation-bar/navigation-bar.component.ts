import { NgIf, NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './navigation-bar.component.html',
})
export class NavigationBarComponent {
  navigationLinks = [
    { title: 'Vendors', link: '/vendors' },
    { title: 'Customers', link: '/customers' },
    { title: 'Settings', link: '/settings' },
  ];

  isRunning: boolean = true;

  constructor(private router: Router) {}

  routeIsActive(route: string): boolean {
    return window.location.pathname === route;
  }
}
