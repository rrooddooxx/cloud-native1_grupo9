import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-my-sales',
  standalone: true,
  imports: [CommonModule, NgbModule],
  template: `
    <div class="container-modern py-5">
      <h1 class="text-brand mb-4">Mis Ventas</h1>
      <div class="row g-4">
        <div class="col-12" *ngFor="let sale of sales">
          <div class="card card-modern">
            <div class="card-body">
              <div class="row align-items-center">
                <div class="col-md-2">
                  <img [src]="sale.imageUrl" [alt]="sale.title" class="img-fluid rounded">
                </div>
                <div class="col-md-6">
                  <h5 class="card-title">{{ sale.title }}</h5>
                  <p class="text-muted">Comprador: {{ sale.buyer }}</p>
                  <small class="text-muted">{{ sale.date | date:'dd/MM/yyyy HH:mm' }}</small>
                </div>
                <div class="col-md-2">
                  <span class="badge" [class]="'bg-' + getStatusColor(sale.status)">{{ sale.status }}</span>
                </div>
                <div class="col-md-2 text-end">
                  <h5 class="text-success">{{ formatPrice(sale.amount) }}</h5>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class MySalesComponent {
  sales = [
    { id: '1', title: 'AirPods Pro', buyer: 'Carlos López', amount: 249000, status: 'completed', date: new Date(), imageUrl: 'https://images.unsplash.com/photo-1588423771073-b8903fbb85b5?w=100&h=100&fit=crop' },
    { id: '2', title: 'iPad Pro', buyer: 'Ana Martínez', amount: 899000, status: 'pending', date: new Date(), imageUrl: 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=100&h=100&fit=crop' }
  ];

  formatPrice(price: number): string {
    return new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' }).format(price);
  }

  getStatusColor(status: string): string {
    switch(status) {
      case 'completed': return 'success';
      case 'pending': return 'warning';
      case 'cancelled': return 'danger';
      default: return 'secondary';
    }
  }
}