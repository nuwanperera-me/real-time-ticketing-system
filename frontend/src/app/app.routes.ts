import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { VendorsComponent } from './pages/vendors/vendors.component';
import { CustomersComponent } from './pages/customers/customers.component';
import { LogsPageComponent } from './pages/logs-page/logs-page.component';
import { SettingsComponent } from './pages/settings/settings.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'vendors', component: VendorsComponent },
  { path: 'customers', component: CustomersComponent },
  { path: 'settings', component: SettingsComponent },
  { path: 'logs', component: LogsPageComponent },
];
