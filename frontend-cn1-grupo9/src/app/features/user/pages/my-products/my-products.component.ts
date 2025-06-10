import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-my-products',
  standalone: true,
  imports: [CommonModule, RouterLink, NgbModule],
  template: `
    <div class="container-modern py-5">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="text-brand">Mis Productos</h1>
        <a routerLink="/user/products/add" class="btn btn-primary-custom">
          <i class="bi bi-plus-circle me-2"></i>Agregar Producto
        </a>
      </div>
      <div class="row g-4">
        <div class="col-md-6 col-lg-4" *ngFor="let product of products">
          <div class="card card-modern">
            <img [src]="product.imageUrl" [alt]="product.title" class="card-img-top" style="height: 200px; object-fit: cover;">
            <div class="card-body">
              <h5 class="card-title">{{ product.title }}</h5>
              <p class="card-text">{{ product.description }}</p>
              <p class="h5 text-primary">{{ formatPrice(product.price) }}</p>
              <div class="d-flex gap-2">
                <button class="btn btn-outline-primary btn-sm">Editar</button>
                <button class="btn btn-outline-danger btn-sm">Eliminar</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .card-modern {
      transition: var(--transition);
      &:hover { transform: translateY(-4px); }
    }
  `]
})
export class MyProductsComponent {
  products = [
    { id: '1', title: 'iPhone 15', description: 'Excelente estado', imageUrl: 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=300&fit=crop', price: 1200000 },
    { id: '2', title: 'MacBook Pro', description: 'Como nuevo', imageUrl: 'https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=400&h=300&fit=crop', price: 2500000 }
  ];

  formatPrice(price: number): string {
    return new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' }).format(price);
  }
}