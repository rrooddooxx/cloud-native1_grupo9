import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DashboardService } from '../../services/dashboard.service';
import { DashboardReport } from '../../../../core/models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, NgbModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  private dashboardService = inject(DashboardService);
  
  stats = {
    myProducts: 0,
    totalSales: 0,
    totalPurchases: 0,
    revenue: 0,
    currency: 'CLP'
  };

  recentActivity = [
    { type: 'sale', product: 'iPhone 15 Pro', amount: 1299000, date: new Date() },
    { type: 'purchase', product: 'MacBook Air', amount: 1499000, date: new Date() },
    { type: 'product_added', product: 'AirPods Pro', date: new Date() }
  ];

  ngOnInit() {
    this.loadDashboardData();
  }

  private loadDashboardData() {
    this.dashboardService.getUserReport().subscribe({
      next: (report: DashboardReport) => {
        this.stats = {
          myProducts: report.totalProducts,
          totalSales: report.totalSales,
          totalPurchases: report.totalPurchases,
          revenue: report.totalIncomeAmount,
          currency: report.totalIncomeAmountCurrency
        };
      },
      error: (error) => {
        console.error('Error loading dashboard data:', error);
      }
    });
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('es-CL', {
      style: 'currency',
      currency: this.stats.currency as any
    }).format(amount);
  }
}