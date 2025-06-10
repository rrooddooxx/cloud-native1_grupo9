import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, NgbModule],
  template: `
    <div class="container-modern py-5">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <div class="card card-modern">
            <div class="card-header">
              <h3 class="text-brand mb-0">Agregar Nuevo Producto</h3>
            </div>
            <div class="card-body">
              <form [formGroup]="productForm" (ngSubmit)="onSubmit()">
                <div class="mb-3">
                  <label class="form-label">Título *</label>
                  <input type="text" class="form-control" formControlName="title" placeholder="Nombre del producto">
                </div>
                <div class="mb-3">
                  <label class="form-label">Descripción *</label>
                  <textarea class="form-control" rows="3" formControlName="description" placeholder="Describe tu producto"></textarea>
                </div>
                <div class="mb-3">
                  <label class="form-label">URL de Imagen *</label>
                  <input type="url" class="form-control" formControlName="imageUrl" placeholder="https://...">
                </div>
                <div class="mb-3">
                  <label class="form-label">Precio *</label>
                  <input type="number" class="form-control" formControlName="price" placeholder="0">
                </div>
                <div class="mb-3">
                  <div class="form-check">
                    <input type="checkbox" class="form-check-input" formControlName="hasPromotion">
                    <label class="form-check-label">¿Tiene promoción?</label>
                  </div>
                </div>
                <div *ngIf="productForm.get('hasPromotion')?.value" class="mb-3">
                  <div class="row">
                    <div class="col-md-6">
                      <label class="form-label">Descuento (%)</label>
                      <input type="number" class="form-control" formControlName="discount" placeholder="15" min="1" max="99">
                    </div>
                    <div class="col-md-6">
                      <label class="form-label">Fecha fin</label>
                      <input type="datetime-local" class="form-control" formControlName="endsAt">
                    </div>
                  </div>
                </div>
                <div class="d-flex gap-3">
                  <button type="submit" class="btn btn-primary-custom" [disabled]="isLoading || productForm.invalid">
                    <span *ngIf="isLoading" class="spinner-border spinner-border-sm me-2"></span>
                    {{ isLoading ? 'Guardando...' : 'Crear Producto' }}
                  </button>
                  <a routerLink="/user/products" class="btn btn-outline-secondary">Cancelar</a>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class AddProductComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  
  isLoading = false;
  productForm: FormGroup;

  constructor() {
    this.productForm = this.fb.group({
      title: ['', [Validators.required]],
      description: ['', [Validators.required]],
      imageUrl: ['', [Validators.required]],
      price: ['', [Validators.required, Validators.min(1)]],
      hasPromotion: [false],
      discount: [''],
      endsAt: ['']
    });
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      this.isLoading = true;
      setTimeout(() => {
        this.isLoading = false;
        this.router.navigate(['/user/products']);
      }, 1500);
    }
  }
}