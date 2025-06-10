import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, NgbModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  stats = {
    myProducts: 12,
    totalSales: 8,
    totalPurchases: 5,
    revenue: 2450000
  };

  recentActivity = [
    { type: 'sale', product: 'iPhone 15 Pro', amount: 1299000, date: new Date() },
    { type: 'purchase', product: 'MacBook Air', amount: 1499000, date: new Date() },
    { type: 'product_added', product: 'AirPods Pro', date: new Date() }
  ];

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('es-CL', {
      style: 'currency',
      currency: 'CLP'
    }).format(amount);
  }
}